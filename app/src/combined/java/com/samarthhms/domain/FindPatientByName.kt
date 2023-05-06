package com.samarthhms.domain

import android.util.Log
import com.samarthhms.repository.PatientRepositoryImpl
import com.samarthhms.usecase.UseCase
import javax.inject.Inject

class FindPatientByName
@Inject constructor(private val patientRepository: PatientRepositoryImpl) : UseCase<FindPatientByNameResponse, FindPatientByNameRequest>(){

    override suspend fun run(params: FindPatientByNameRequest): FindPatientByNameResponse {
        return try {
            val response = FindPatientByNameResponse()
            response.data = patientRepository.findPatientsByName(params.firstName, params.middleName, params.lastName)
            response.status = Status.SUCCESS
            Log.i("Find_Patient_By_Name","FindPatientByNameResponse = $response")
            response
        } catch (e: Exception){
            Log.e("Find_Patient_By_Name","Error while finding patients by name : $e")
            val response = FindPatientByNameResponse()
            response.status = Status.FAILURE
            return response
        }
    }
}