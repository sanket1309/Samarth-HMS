package com.samarthhms.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.samarthhms.constants.SchemaName
import com.samarthhms.domain.GetPatientsToday
import com.samarthhms.domain.Status
import com.samarthhms.models.*
import com.samarthhms.models.PatientFirebase
import com.samarthhms.usecase.UseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AdminDashboardViewModel @Inject constructor(val getPatientsToday: GetPatientsToday) : ViewModel() {

    private val _getPatientsTodayStatus: MutableLiveData<Status> = MutableLiveData(Status.NONE)
    val getPatientsTodayStatus : LiveData<Status> = _getPatientsTodayStatus

    private val _patientsTodayList: MutableLiveData<List<PatientVisitInfo>> = MutableLiveData(listOf())
    val patientsTodayList : LiveData<List<PatientVisitInfo>> = _patientsTodayList

    private val _patientsTodayCount = MutableLiveData(0)
    val patientsTodayCount : LiveData<Int> = _patientsTodayCount

    private val _unattendedPatientsCount = MutableLiveData(0)
    val unattendedPatientsCount : LiveData<Int> = _unattendedPatientsCount

    private val _admitPatientsCount = MutableLiveData(0)
    val admitPatientsCount : LiveData<Int> = _admitPatientsCount

    fun updateData(){
        getPatientsToday(UseCase.None()) {
            _getPatientsTodayStatus.value = it.status
            if(it.data == null){
                return@getPatientsToday
            }
            _patientsTodayList.value = it.data
            _patientsTodayCount.value = it.data?.size
            _unattendedPatientsCount.value = it.unattendedPatientsCount
        }
    }

    fun addListener(){
        val db = FirebaseFirestore.getInstance()
        val reference = db.collection(SchemaName.VISITS_COLLECTION)
        reference.addSnapshotListener{
            _,_ -> updateData()
        }
    }
}