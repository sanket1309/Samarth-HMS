package com.samarthhms.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.samarthhms.domain.*
import com.samarthhms.models.DateRange
import com.samarthhms.models.Name
import com.samarthhms.models.Patient
import com.samarthhms.models.PatientVisitInfo
import com.samarthhms.utils.StringUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchPatientViewModel @Inject constructor(val searchPatient: SearchPatient) : ViewModel() {

    private val _getPatientsStatus: MutableLiveData<Status> = MutableLiveData(Status.NONE)
    val getPatientsStatus : LiveData<Status> = _getPatientsStatus

    private val _patientsList: MutableLiveData<List<PatientVisitInfo>> = MutableLiveData(listOf())
    val patientsList : LiveData<List<PatientVisitInfo>> = _patientsList

    fun getPatients(request: SearchPatientByNameRequest){
        searchPatient(request) {
            _getPatientsStatus.value = it.status
            if(it.patientVisitInfoList == null){
                return@searchPatient
            }
            _patientsList.value = it.patientVisitInfoList
        }
    }
}