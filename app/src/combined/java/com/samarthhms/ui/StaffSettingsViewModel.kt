package com.samarthhms.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.samarthhms.domain.*
import com.samarthhms.models.Staff
import com.samarthhms.models.StaffDetails
import com.samarthhms.models.StaffStatus
import com.samarthhms.models.SwitchAdminData
import com.samarthhms.usecase.UseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StaffSettingsViewModel
@Inject constructor(val updateStaffStatus: UpdateStaffStatus, val getAllStaff: GetAllStaff) : ViewModel() {

    private val _staff: MutableLiveData<List<StaffDetails>> = MutableLiveData(listOf())
    val staff : LiveData<List<StaffDetails>> = _staff

    private val _getStaffStatus: MutableLiveData<Status> = MutableLiveData(Status.NONE)
    val getStaffStatus : LiveData<Status> = _getStaffStatus

    fun getAllStaff(){
        getAllStaff(UseCase.None()){
            _staff.value = it.data
        }
    }

    fun updateLockStatus(staffStaff: StaffStatus){
        _getStaffStatus.value = Status.NONE
        updateStaffStatus(staffStaff){
            _getStaffStatus.value = it.status
            getAllStaff()
        }
    }
}