package com.samarthhms.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.samarthhms.domain.AddAdmin
import com.samarthhms.domain.Status
import com.samarthhms.models.AdminDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddAdminViewModel @Inject constructor(private val addAdmin: AddAdmin) : ViewModel(){
    private val _addAdminStatus = MutableLiveData(Status.NONE)
    val addAdminStatus : LiveData<Status> = _addAdminStatus

    fun addAdmin(adminDetails: AdminDetails){
        addAdmin(adminDetails){
            _addAdminStatus.value = it.status
        }
    }
}