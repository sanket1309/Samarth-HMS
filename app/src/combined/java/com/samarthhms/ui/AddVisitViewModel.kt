package com.samarthhms.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.samarthhms.domain.AddPatientVisit
import com.samarthhms.domain.AddPatientVisitResponse
import com.samarthhms.domain.Status
import com.samarthhms.models.Patient
import com.samarthhms.models.PatientVisitDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddVisitViewModel @Inject constructor(private val addPatientVisit: AddPatientVisit) : ViewModel(){
    private val _addVisitStatus = MutableLiveData(Status.NONE)
    val addVisitStatus : LiveData<Status> = _addVisitStatus

    fun addVisit(patientVisitDetails: PatientVisitDetails){
        addPatientVisit(patientVisitDetails){
            _addVisitStatus.value = it.status
        }
    }
}