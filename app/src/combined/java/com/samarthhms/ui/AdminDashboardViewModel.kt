package com.samarthhms.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.samarthhms.constants.Role
import com.samarthhms.constants.SchemaName
import com.samarthhms.domain.GetPatientsToday
import com.samarthhms.domain.GetUser
import com.samarthhms.domain.GetUserResponse
import com.samarthhms.domain.Status
import com.samarthhms.models.*
import com.samarthhms.models.PatientFirebase
import com.samarthhms.usecase.UseCase
import com.samarthhms.utils.DateTimeUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AdminDashboardViewModel @Inject constructor(val getPatientsToday: GetPatientsToday, val getUser: GetUser): ViewModel() {

    private val _getPatientsTodayStatus: MutableLiveData<Status> = MutableLiveData(Status.NONE)
    val getPatientsTodayStatus : LiveData<Status> = _getPatientsTodayStatus

    private val _patientsTodayList: MutableLiveData<List<PatientVisitInfo>> = MutableLiveData(listOf())
    val patientsTodayList : LiveData<List<PatientVisitInfo>> = _patientsTodayList

    private val _patientsTodayCount = MutableLiveData(0)
    val patientsTodayCount : LiveData<Int> = _patientsTodayCount

    private val _unattendedPatientsCount = MutableLiveData(0)
    val unattendedPatientsCount : LiveData<Int> = _unattendedPatientsCount

    private val _admitPatientsCount = MutableLiveData(0)
    val admitPatientsCount : LiveData<Int> = _admitPatientsCount

    private val _greeting = MutableLiveData("")
    val greeting : LiveData<String> = _greeting

    private val _userName = MutableLiveData("")
    val userName : LiveData<String> = _userName

    fun updateGreetings(){
        _greeting.value = getGreeting()
        getUser(UseCase.None()){
            val firstName = if(it.userRole == Role.ADMIN){
                it.admin?.firstName?:""
            } else if(it.userRole == Role.STAFF){
                it.staff?.firstName?:""
            } else{
                ""
            }
            _userName.value = if(firstName!="") "Dr. "+firstName else ""
        }
    }

    fun getGreeting(): String{
        val hours = DateTimeUtils.getHours()
        return if(hours in 0 until 12) "Good Morning,"
        else if(hours in 12 until 18) "Good Afternoon,"
        else "Good Evening,"
    }

    fun updateData(){
        getPatientsToday(UseCase.None()) {
            _getPatientsTodayStatus.value = it.status
            if(it.data == null){
                return@getPatientsToday
            }
            _patientsTodayList.value = it.data
            _patientsTodayCount.value = it.data?.size
            _unattendedPatientsCount.value = it.unattendedPatientsCount
        }
    }

    fun addListener(){
        val db = FirebaseFirestore.getInstance()
        val reference = db.collection(SchemaName.VISITS_COLLECTION)
        reference.addSnapshotListener{
            _,_ -> updateData()
        }
    }
}