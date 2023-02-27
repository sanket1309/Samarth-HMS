package com.samarthhms.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.samarthhms.constants.SchemaName
import com.samarthhms.domain.FindPatientByContactNumber
import com.samarthhms.domain.GetPatientsToday
import com.samarthhms.domain.Status
import com.samarthhms.models.Patient
import com.samarthhms.models.PatientVisitInfo
import com.samarthhms.usecase.UseCase
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
            Log.i("VIEWMODEL","STATUS : ${it.status}\nDATA FETCHED : ${it.data}")
            if(it.data == null){
                return@findPatientByContactNumber
            }
            _patientsList.value = it.data
        }
    }
}