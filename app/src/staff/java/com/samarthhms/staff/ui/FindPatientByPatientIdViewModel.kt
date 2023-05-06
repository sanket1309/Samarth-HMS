package com.samarthhms.staff.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.samarthhms.staff.domain.FindPatientByPatientId
import com.samarthhms.staff.domain.Status
import com.samarthhms.staff.models.Patient
import com.samarthhms.staff.models.PatientVisitInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FindPatientByPatientIdViewModel @Inject constructor(val findPatientByPatientId: FindPatientByPatientId) : ViewModel() {
    private val _patient: MutableLiveData<Patient?> = MutableLiveData(null)
    val patient : LiveData<Patient?> = _patient

    private val _searchStatus: MutableLiveData<Status> = MutableLiveData(Status.NONE)
    val searchStatus : LiveData<Status> = _searchStatus

    fun findPatient(patientId: String){
        findPatientByPatientId(patientId){
            _patient.value = it.data
            _searchStatus.value = it.status
        }
    }
}