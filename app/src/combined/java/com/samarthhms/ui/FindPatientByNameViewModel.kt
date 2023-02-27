package com.samarthhms.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.samarthhms.domain.*
import com.samarthhms.models.Patient
import com.samarthhms.utils.StringUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FindPatientByNameViewModel @Inject constructor(val findPatientByName: FindPatientByName) : ViewModel() {

    private val _getPatientsStatus: MutableLiveData<Status> = MutableLiveData(Status.NONE)
    val getPatientsStatus : LiveData<Status> = _getPatientsStatus

    private val _patientsList: MutableLiveData<List<Patient>> = MutableLiveData(listOf())
    val patientsList : LiveData<List<Patient>> = _patientsList

    fun getPatients(firstName: String, middleName: String, lastName: String){
        val request = FindPatientByNameRequest()
        request.firstName = StringUtils.formatName(firstName)
        request.middleName = StringUtils.formatName(middleName)
        request.lastName = StringUtils.formatName(lastName)
        findPatientByName(request) {
            _getPatientsStatus.value = it.status
            Log.i("VIEWMODEL","STATUS : ${it.status}\nDATA FETCHED : ${it.data}")
            if(it.data == null){
                return@findPatientByName
            }
            _patientsList.value = it.data
        }
    }
}