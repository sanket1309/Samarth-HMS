package com.samarthhms.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.samarthhms.domain.FindPatientByContactNumber
import com.samarthhms.domain.Status
import com.samarthhms.models.Patient
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FindPatientByContactNumberViewModel @Inject constructor(val findPatientByContactNumber: FindPatientByContactNumber) : ViewModel() {

    private val _getPatientsStatus: MutableLiveData<Status> = MutableLiveData(Status.NONE)
    val getPatientsStatus : LiveData<Status> = _getPatientsStatus

    private val _patientsList: MutableLiveData<List<Patient>> = MutableLiveData(listOf())
    val patientsList : LiveData<List<Patient>> = _patientsList

    fun getPatients(contactNumber: String){
        findPatientByContactNumber(contactNumber) {
            _getPatientsStatus.value = it.status
            if(it.data == null){
                return@findPatientByContactNumber
            }
            _patientsList.value = it.data
        }
    }
}