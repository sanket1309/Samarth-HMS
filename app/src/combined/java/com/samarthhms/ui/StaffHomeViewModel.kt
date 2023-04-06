package com.samarthhms.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.type.DateTime
import com.samarthhms.constants.Role
import com.samarthhms.constants.SchemaName
import com.samarthhms.domain.*
import com.samarthhms.models.PatientVisitInfo
import com.samarthhms.models.StaffStatus
import com.samarthhms.repository.StoredStateRepositoryImpl
import com.samarthhms.usecase.UseCase
import com.samarthhms.utils.DateTimeUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StaffHomeViewModel @Inject constructor(val getRecentVisit: GetRecentVisit, val getUser: GetUser, val storedStateRepositoryImpl: StoredStateRepositoryImpl): ViewModel() {

    private val _getRecentVisitStatus: MutableLiveData<Status> = MutableLiveData(Status.NONE)
    val getRecentVisitStatus : LiveData<Status> = _getRecentVisitStatus

    private val _recentVisit: MutableLiveData<PatientVisitInfo?> = MutableLiveData(null)
    val recentVisit : LiveData<PatientVisitInfo?> = _recentVisit

    private val _isLocked = MutableLiveData(false)
    val isLocked : LiveData<Boolean> = _isLocked

    private val _greeting = MutableLiveData("")
    val greeting : LiveData<String> = _greeting

    private val _userName = MutableLiveData("")
    val userName : LiveData<String> = _userName

    private val _day = MutableLiveData("")
    val day : LiveData<String> = _day

    private val _date = MutableLiveData("")
    val date : LiveData<String> = _date

    fun updateGreetingsAndInfo(){
        _greeting.value = getGreeting()
        getUser(UseCase.None()){
            var firstName:String = ""
            var prefix:String = ""
            if(it.userRole == Role.ADMIN){
                firstName = it.admin?.firstName?:""
                prefix = "Dr. "
            } else if(it.userRole == Role.STAFF){
                firstName = it.staff?.firstName?:""
            }
            _userName.value = if(firstName!="") prefix+firstName else ""
            _day.value = DateTimeUtils.getDay()
            _date.value = DateTimeUtils.getDate()
        }
    }

    fun getGreeting(): String{
        val hours = DateTimeUtils.getHours()
        return if(hours in 0 until 12) "Good Morning,"
        else if(hours in 12 until 18) "Good Afternoon,"
        else "Good Evening,"
    }

    fun updateData(){
        getRecentVisit(UseCase.None()) {
            Log.d("DEBUG_STAFF", "STATUS : ${it.status}, DATA : ${it.data}")
            _getRecentVisitStatus.value = it.status
            if (it.data.isEmpty()) {
                _recentVisit.value = null
                return@getRecentVisit
            }
            _recentVisit.value = it.data!!.first()
        }
    }

    init {
        val db = FirebaseFirestore.getInstance()
        var id: String=""
        GlobalScope.launch {
            id = storedStateRepositoryImpl.getId()!!
            val reference = db.collection(SchemaName.STAFF_STATUS_COLLECTION).whereEqualTo(SchemaName.STAFF_ID, id)
            reference.addSnapshotListener{
                 snapshot,_ ->
                _isLocked.value = snapshot?.toObjects(StaffStatus::class.java)?.first()?.isLocked
            }
        }
    }
}