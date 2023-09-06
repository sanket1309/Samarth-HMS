package com.samarthhms.domain

import android.util.Log
import com.samarthhms.models.Patient
import com.samarthhms.models.PatientVisitInfo
import com.samarthhms.repository.PatientRepositoryImpl
import com.samarthhms.repository.VisitRepository
import com.samarthhms.repository.VisitRepositoryImpl
import com.samarthhms.usecase.UseCase
import java.util.Objects
import javax.inject.Inject

class SearchPatient
@Inject constructor(private val patientRepository: PatientRepositoryImpl, private val visitRepository: VisitRepositoryImpl) : UseCase<SearchPatientByNameResponse, SearchPatientByNameRequest>(){

    override suspend fun run(params: SearchPatientByNameRequest): SearchPatientByNameResponse {
        return try {
            val response = SearchPatientByNameResponse()
            if(Objects.isNull(params.dateRange)){
//                val patients = patientRepository.searchPatients(params.name!!, params.contactNumber, params.town)
//                response.patientVisitInfoList = patients
            }else{
                val visits = visitRepository.getVisitsByDate(params.dateRange!!)
                val patients = patientRepository.searchPatientByPatientId(visits.map { it.patientId }, params.name!!, params.contactNumber, params.town)

                val mapOfPatientIdToPatient = HashMap<String, Patient>()
                patients.forEach { mapOfPatientIdToPatient[it.patientId] = it }
                val patientVisitInfoList = ArrayList<PatientVisitInfo>()
                visits.forEach {patientVisitInfoList.add(PatientVisitInfo(mapOfPatientIdToPatient[it.patientId]!!,it.visitTime!!))}
                patientVisitInfoList.sortByDescending { it.visitTime }
                response.status = Status.SUCCESS
                response.patientVisitInfoList = patientVisitInfoList
            }
            response.status = Status.SUCCESS
            Log.i("Search_Patient","Search patient status = ${response.status}")
            response
        } catch (e: Exception){
            Log.e("Search_Patient","Error while searching patient : ",e)
            val response = SearchPatientByNameResponse()
            response.status = Status.FAILURE
            return response
        }
    }
}