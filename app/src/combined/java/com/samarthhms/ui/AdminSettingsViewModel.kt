package com.samarthhms.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.samarthhms.domain.*
import com.samarthhms.models.Admin
import com.samarthhms.models.AdminDetails
import com.samarthhms.models.StaffDetails
import com.samarthhms.models.StaffStatus
import com.samarthhms.usecase.UseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AdminSettingsViewModel
@Inject constructor(val getAllAdmins: GetAllAdmins) : ViewModel() {

    private val _admin: MutableLiveData<List<AdminDetails>> = MutableLiveData(listOf())
    val admin : LiveData<List<AdminDetails>> = _admin

    private val _getAdminStatus: MutableLiveData<Status> = MutableLiveData(Status.NONE)
    val getAdminStatus : LiveData<Status> = _getAdminStatus

    fun getAllAdmins(){
        getAllAdmins(UseCase.None()){
            _admin.value = it.data
        }
    }
}