package com.samarthhms.staff.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.samarthhms.staff.constants.SchemaName
import com.samarthhms.staff.domain.*
import com.samarthhms.staff.models.StaffStatus
import com.samarthhms.staff.repository.StoredStateRepositoryImpl
import com.samarthhms.staff.usecase.UseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StaffLockViewModel @Inject constructor(private val logoutUser: LogoutUser, private val storedStateRepository: StoredStateRepositoryImpl) : ViewModel(){

    private val _logoutUserStatus = MutableLiveData(Status.NONE)
    val logoutUserStatus : LiveData<Status> = _logoutUserStatus

    private val _isLocked = MutableLiveData(true)
    val isLocked : LiveData<Boolean> = _isLocked

    fun logout(){
        logoutUser(UseCase.None()){
            onLogoutResponse(it)
        }
    }

    private fun onLogoutResponse(logoutResponse: LogoutResponse){
        _logoutUserStatus.value = logoutResponse.logoutResponseStatus
    }

    init {
        val db = FirebaseFirestore.getInstance()
        var id: String=""
        GlobalScope.launch {
            id = storedStateRepository.getId()!!
            val reference = db.collection(SchemaName.STAFF_STATUS_COLLECTION).whereEqualTo(
                SchemaName.STAFF_ID, id)
            reference.addSnapshotListener{
                    snapshot,_ ->
                val isLocked = snapshot?.toObjects(StaffStatus::class.java)?.first()?.isLocked?: false
                if(!isLocked){
                    _isLocked.value = false
                }
            }
        }
    }
}