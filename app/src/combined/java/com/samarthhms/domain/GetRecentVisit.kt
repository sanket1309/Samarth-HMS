package com.samarthhms.domain

import android.util.Log
import com.samarthhms.models.Patient
import com.samarthhms.models.PatientVisitInfo
import com.samarthhms.repository.*
import com.samarthhms.usecase.UseCase
import java.time.LocalDateTime
import javax.inject.Inject

class GetRecentVisit
@Inject constructor(private val patientRepository: PatientRepositoryImpl, private val visitRepository: VisitRepositoryImpl, private val storedStateRepository: StoredStateRepositoryImpl) : UseCase<GetRecentVisitResponse, UseCase.None>(){

    val RECENT_VISIT_THRESHOLD_TIME_IN_HOURS: Long = 1

    override suspend fun run(params: None): GetRecentVisitResponse {
        return try {
            val response = GetRecentVisitResponse()
            val id = if(storedStateRepository.isSwitchStatePresent()) storedStateRepository.getSwitchAdminState().adminId else storedStateRepository.getId()
            val visitsToday = visitRepository.getVisitsByAttendentAfter(id!!,LocalDateTime.now().minusHours(RECENT_VISIT_THRESHOLD_TIME_IN_HOURS))
            val patientsToday = patientRepository.findPatientByPatientId(visitsToday.map { it.patientId })
            val mapOfPatientIdToPatient = HashMap<String, Patient>()
            patientsToday.forEach { mapOfPatientIdToPatient[it.patientId] = it }
            val patientVisitInfoList = ArrayList<PatientVisitInfo>()
            visitsToday.forEach {patientVisitInfoList.add(PatientVisitInfo(mapOfPatientIdToPatient[it.patientId]!!,it.visitTime))}
            patientVisitInfoList.sortByDescending { it.visitTime }
            response.status = Status.SUCCESS
            response.data = patientVisitInfoList
            Log.i("Get_Recent_Visit","GetRecentVisitResponse = $response")
            response
        } catch (e: Exception){
            Log.e("Get_Recent_Visit","Error while getting recent visit : $e")
            val response = GetRecentVisitResponse()
            response.status = Status.FAILURE
            return response
        }
    }
}