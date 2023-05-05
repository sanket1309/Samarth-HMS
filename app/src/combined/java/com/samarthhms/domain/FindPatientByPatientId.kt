package com.samarthhms.domain

import android.util.Log
import com.samarthhms.repository.PatientRepositoryImpl
import com.samarthhms.usecase.UseCase
import javax.inject.Inject

class FindPatientByPatientId
@Inject constructor(private val patientRepository: PatientRepositoryImpl) : UseCase<FindPatientByPatientIdResponse, String>(){

    override suspend fun run(params: String): FindPatientByPatientIdResponse {
        return try {
            val response = FindPatientByPatientIdResponse()
            response.data = patientRepository.findPatientByPatientId(params)
            response.status = Status.SUCCESS
            Log.i("Find_Patient_By_Patient_Id","FindPatientByPatientIdResponse = $response")
            response
        } catch (e: Exception){
            Log.e("Find_Patient_By_Patient_Id","Error while finding patient by patient id : $e")
            val response = FindPatientByPatientIdResponse()
            response.status = Status.FAILURE
            return response
        }
    }
}