package com.samarthhms.domain

import android.util.Log
import com.samarthhms.models.Staff
import com.samarthhms.models.StaffDetails
import com.samarthhms.models.StaffStatus
import com.samarthhms.repository.*
import com.samarthhms.usecase.UseCase
import javax.inject.Inject

class RemoveStaff
@Inject constructor(private val staffRepository: StaffRepositoryImpl, private val staffStatusRepository: StaffStatusRepositoryImpl, private val loginRepository: LoginRepositoryImpl) : UseCase<RemoveStaffResponse, String>(){

    override suspend fun run(params: String): RemoveStaffResponse {
        return try {
            val response = RemoveStaffResponse()
            staffRepository.removeStaff(params)
            staffStatusRepository.removeStaffStatus(params)
            loginRepository.removeCredentials(params)
            response.status = Status.SUCCESS
            Log.i("Remove_Staff","RemoveStaffResponse = $response")
            response
        } catch (e: Exception){
            Log.e("Remove_Staff","Error while removing staff : $e")
            val response = RemoveStaffResponse()
            response.status = Status.FAILURE
            return response
        }
    }
}