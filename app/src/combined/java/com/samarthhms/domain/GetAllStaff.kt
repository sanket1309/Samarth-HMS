package com.samarthhms.domain

import android.util.Log
import com.samarthhms.models.Patient
import com.samarthhms.models.PatientVisitInfo
import com.samarthhms.repository.*
import com.samarthhms.usecase.UseCase
import java.time.LocalDateTime
import javax.inject.Inject

class GetAllStaff
@Inject constructor(private val staffRepository: StaffRepositoryImpl) : UseCase<GetAllStaffResponse, UseCase.None>(){

    override suspend fun run(params: None): GetAllStaffResponse {
        return try {
            val response = GetAllStaffResponse()
            val staff = staffRepository.getAllStaff()
            response.status = Status.SUCCESS
            response.data = staff
            Log.i("Get_All_Staff","GetAllStaffResponse = $response")
            response
        } catch (e: Exception){
            Log.e("Get_All_Staff","Error while getting all staff : $e")
            val response = GetAllStaffResponse()
            response.status = Status.FAILURE
            return response
        }
    }
}