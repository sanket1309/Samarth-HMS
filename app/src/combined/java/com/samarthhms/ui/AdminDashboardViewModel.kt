package com.samarthhms.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.samarthhms.constants.SchemaName
import com.samarthhms.models.*
import com.samarthhms.models.PatientFirebase

//@HiltViewModel
class AdminDashboardViewModel : ViewModel() {
//    private val _patientsTodayList: MutableLiveData<List<PatientVisitInfo>> = MutableLiveData(listOf())
//    val patientsTodayList : LiveData<List<PatientVisitInfo>> = _patientsTodayList

    private val _patientsTodayList: MutableLiveData<List<Patient>> = MutableLiveData(listOf())
    val patientsTodayList : LiveData<List<Patient>> = _patientsTodayList

    private val _patientsTodayCount = MutableLiveData(0)
    val patientsTodayCount : LiveData<Int> = _patientsTodayCount

    private val _unattendedPatientsCount = MutableLiveData(0)
    val unattendedPatientsCount : LiveData<Int> = _unattendedPatientsCount

    private val _admitPatientsCount = MutableLiveData(0)
    val admitPatientsCount : LiveData<Int> = _admitPatientsCount

    fun getData(){
        val db = FirebaseFirestore.getInstance()
        val refVisits = db.collection(SchemaName.VISITS_COLLECTION).orderBy("visitTime",Query.Direction.DESCENDING)
        val refPatients = db.collection(SchemaName.PATIENTS_COLLECTION)

        val query = refPatients.whereIn("patientId", listOf("SBP00001","SBP00002","SBP00003","SBP00004","SBP00005")).orderBy("patientId",Query.Direction.DESCENDING)
        query.addSnapshotListener{
            value, _ ->
            if(value==null) return@addSnapshotListener
            _patientsTodayList.value = value?.map { Converters.convertToPatient(it.toObject(
                PatientFirebase::class.java)) }
            _patientsTodayCount.value = value?.count()
        }

//        refVisits.addSnapshotListener{
//            docs, _ ->
//            val visits = docs!!.map {
//                it.toObject(VisitFirebase::class.java)
//            }
//            _patientsTodayCount.value = visits.size
//            _unattendedPatientsCount.value = visits.count { !it.isAttended }
//            val patientIds = visits.map { it.patientId }
//            val patientIdToVisitTimeMap = HashMap<String, LocalDateTime>()
//            visits.map {
//                patientIdToVisitTimeMap.put(it.patientId, DateTimeUtils.getLocalDateTime(it.visitTime))
//            }
//            val query = refPatients.whereIn("patientId",patientIds)
//            viewModelScope.launch {
//                val patientVisits = query.get().await().documents.map {
//                    val patient = Converters.convertToPatient(it.toObject(PatientFirebase::class.java)!!)
//                    PatientVisitInfo(patient,patientIdToVisitTimeMap.get(patient.patientId)!!)
//                }
//                _patientsTodayList.value = patientVisits.sortedBy { it.visitTime }.reversed()
//            }
//        }
    }
}