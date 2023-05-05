package com.samarthhms.staff.domain

import android.util.Log
import com.samarthhms.staff.models.Patient
import com.samarthhms.staff.models.PatientVisitInfo
import com.samarthhms.staff.repository.*
import com.samarthhms.staff.usecase.UseCase
import java.time.LocalDateTime
import javax.inject.Inject

class FindPatientByContactNumber
@Inject constructor(private val patientRepository: PatientRepositoryImpl) : UseCase<FindPatientByContactNumberResponse, String>(){

    override suspend fun run(params: String): FindPatientByContactNumberResponse {
        return try {
            val response = FindPatientByContactNumberResponse()
            response.data = patientRepository.findPatientsByContactNumber(params)
            response.status = Status.SUCCESS
            Log.i("Find_Patient_By_Contact_Number","FindPatientByContactNumberResponse = $response")
            response
        } catch (e: Exception){
            Log.e("Find_Patient_By_Contact_Number","Error while finding patient by contact number : $e")
            val response = FindPatientByContactNumberResponse()
            response.status = Status.FAILURE
            return response
        }
    }
}