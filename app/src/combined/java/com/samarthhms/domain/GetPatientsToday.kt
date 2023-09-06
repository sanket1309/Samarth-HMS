package com.samarthhms.domain

import android.util.Log
import com.samarthhms.models.Patient
import com.samarthhms.models.PatientVisitInfo
import com.samarthhms.repository.PatientRepositoryImpl
import com.samarthhms.repository.StoredStateRepositoryImpl
import com.samarthhms.repository.VisitRepositoryImpl
import com.samarthhms.usecase.UseCase
import java.time.LocalDateTime
import javax.inject.Inject

class GetPatientsToday
@Inject constructor(private val patientRepository: PatientRepositoryImpl, private val visitRepository: VisitRepositoryImpl, private val storedStateRepository: StoredStateRepositoryImpl) : UseCase<GetPatientsTodayResponse, UseCase.None>(){

    override suspend fun run(params: None): GetPatientsTodayResponse {
        return try {
            val response = GetPatientsTodayResponse()
            val adminId = if(storedStateRepository.isSwitchStatePresent()) storedStateRepository.getSwitchAdminState().adminId else storedStateRepository.getId()
            val visitsToday = visitRepository.getVisitsOnDateByAdmin(adminId!!,LocalDateTime.now())
            val patientsToday = patientRepository.findPatientByPatientId(visitsToday.map { it.patientId })
            val mapOfPatientIdToPatient = HashMap<String, Patient>()
            patientsToday.forEach { mapOfPatientIdToPatient[it.patientId] = it }
            val patientVisitInfoList = ArrayList<PatientVisitInfo>()
            visitsToday.forEach {patientVisitInfoList.add(PatientVisitInfo(mapOfPatientIdToPatient[it.patientId]!!,it.visitTime!!))}
            patientVisitInfoList.sortByDescending { it.visitTime }
            response.status = Status.SUCCESS
            response.data = patientVisitInfoList
            response.unattendedPatientsCount = visitsToday.count { !it.isAttended }
            Log.i("Get_Patients_Today","GetPatientsTodayResponse = $response")
            response
        } catch (e: Exception){
            Log.e("Get_Patients_Today","Error while getting patients today : $e")
            val response = GetPatientsTodayResponse()
            response.status = Status.FAILURE
            return response
        }
    }
}