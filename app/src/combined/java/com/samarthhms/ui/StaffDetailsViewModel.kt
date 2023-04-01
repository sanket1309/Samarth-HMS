package com.samarthhms.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.samarthhms.domain.*
import com.samarthhms.models.Staff
import com.samarthhms.models.StaffDetails
import com.samarthhms.models.StaffStatus
import com.samarthhms.usecase.UseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StaffDetailsViewModel
@Inject constructor(private val removeStaff: RemoveStaff, val addStaff: AddStaff) : ViewModel() {

    private val _addStaffStatus: MutableLiveData<Status> = MutableLiveData(Status.NONE)
    val addStaffStatus : LiveData<Status> = _addStaffStatus

    private val _removeStaffStatus: MutableLiveData<Status> = MutableLiveData(Status.NONE)
    val removeStaffStatus : LiveData<Status> = _removeStaffStatus

    fun addStaff(staffDetails: StaffDetails){
        val request = AddStaffRequest(staffDetails.staff!!, staffDetails.staffCredentials!!)
        _addStaffStatus.value = Status.NONE
        addStaff(request){
            _addStaffStatus.value = it.status
        }
    }

    fun removeStaff(staffId: String){
        removeStaff(staffId){
            _removeStaffStatus.value = it.status
        }
    }
}