package com.samarthhms.domain

import android.util.Log
import com.samarthhms.models.Patient
import com.samarthhms.models.PatientVisitInfo
import com.samarthhms.models.Staff
import com.samarthhms.models.StaffDetails
import com.samarthhms.repository.*
import com.samarthhms.usecase.UseCase
import java.time.LocalDateTime
import javax.inject.Inject

class GetAllStaff
@Inject constructor(private val storedStateRepository: StoredStateRepositoryImpl,private val staffRepository: StaffRepositoryImpl, private val staffStatusRepository: StaffStatusRepositoryImpl, private val loginRepository: LoginRepositoryImpl) : UseCase<GetAllStaffResponse, UseCase.None>(){

    override suspend fun run(params: None): GetAllStaffResponse {
        return try {
            val response = GetAllStaffResponse()
            val adminId = storedStateRepository.getAdminId()
            val staff = staffRepository.getAllStaff(adminId!!)
            val staffDetailsList = mutableListOf<StaffDetails>()
            staff.map {
                val staffStatus = staffStatusRepository.getStaffStatus(it.staffId)
                val staffCredentials = loginRepository.getCredentials(it.staffId)
                val staffDetails = StaffDetails(it.staffId, it, staffStatus, staffCredentials)
                staffDetailsList.add(staffDetails)
            }
            response.status = Status.SUCCESS
            response.data = staffDetailsList
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