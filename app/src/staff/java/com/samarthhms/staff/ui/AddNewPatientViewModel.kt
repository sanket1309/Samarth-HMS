package com.samarthhms.staff.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.samarthhms.staff.domain.AddNewPatient
import com.samarthhms.staff.domain.AddNewPatientResponse
import com.samarthhms.staff.domain.Status
import com.samarthhms.staff.models.Patient
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddNewPatientViewModel @Inject constructor(private val addNewPatient: AddNewPatient) : ViewModel(){
    private val _addPatientStatus = MutableLiveData(Status.NONE)
    val addPatientStatus : LiveData<Status> = _addPatientStatus

    fun addPatient(patient: Patient){
        addNewPatient(patient){
            onResponse(it)
        }
    }

    private fun onResponse(addNewPatientResponse: AddNewPatientResponse){
        _addPatientStatus.value = addNewPatientResponse.status
    }
}