package com.samarthhms.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.samarthhms.domain.AddAdmin
import com.samarthhms.domain.RemoveAdmin
import com.samarthhms.domain.Status
import com.samarthhms.models.AdminDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AdminDetailsViewModel
@Inject constructor(private val removeAdmin: RemoveAdmin, val addAdmin: AddAdmin) : ViewModel() {

    private val _addAdminStatus: MutableLiveData<Status> = MutableLiveData(Status.NONE)
    val addAdminStatus : LiveData<Status> = _addAdminStatus

    private val _removeAdminStatus: MutableLiveData<Status> = MutableLiveData(Status.NONE)
    val removeAdminStatus : LiveData<Status> = _removeAdminStatus

    fun addAdmin(adminDetails: AdminDetails){
        _addAdminStatus.value = Status.NONE
        addAdmin(adminDetails){
            _addAdminStatus.value = it.status
        }
    }

    fun removeAdmin(adminId: String){
        removeAdmin(adminId){
            _removeAdminStatus.value = it.status
        }
    }
}