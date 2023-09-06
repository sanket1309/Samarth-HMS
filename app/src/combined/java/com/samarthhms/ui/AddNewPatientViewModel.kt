package com.samarthhms.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.samarthhms.domain.AddNewPatient
import com.samarthhms.domain.Status
import com.samarthhms.models.Patient
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddNewPatientViewModel @Inject constructor(private val addNewPatient: AddNewPatient) : ViewModel(){
    private val _addPatientStatus = MutableLiveData(Status.NONE)
    val addPatientStatus : LiveData<Status> = _addPatientStatus

    fun addPatient(patient: Patient){
        addNewPatient(patient){
            _addPatientStatus.value = it.status
        }
    }
}