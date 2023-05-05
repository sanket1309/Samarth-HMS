package com.samarthhms.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.samarthhms.domain.AddStaff
import com.samarthhms.domain.AddStaffRequest
import com.samarthhms.domain.Status
import com.samarthhms.models.Credentials
import com.samarthhms.models.Staff
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddStaffViewModel @Inject constructor(private val addStaff: AddStaff) : ViewModel(){
    private val _addStaffStatus = MutableLiveData(Status.NONE)
    val addStaffStatus : LiveData<Status> = _addStaffStatus

    fun addStaff(staff: Staff, credentials: Credentials){
        val request = AddStaffRequest(staff, credentials)
        addStaff(request){
            _addStaffStatus.value = it.status
        }
    }
}