package com.samarthhms.domain

import android.util.Log
import com.samarthhms.models.StaffStatus
import com.samarthhms.repository.StaffStatusRepositoryImpl
import com.samarthhms.usecase.UseCase
import javax.inject.Inject

class UpdateStaffStatus
@Inject constructor(private val staffStatusRepository: StaffStatusRepositoryImpl) : UseCase<UpdateStaffStatusResponse, StaffStatus>(){
    override suspend fun run(params: StaffStatus): UpdateStaffStatusResponse {
        return try {
            val response = UpdateStaffStatusResponse()
            staffStatusRepository.addStaffStatus(params)
            response.status = Status.SUCCESS
            Log.i("Get_All_Staff","GetAllStaffResponse = $response")
            response
        } catch (e: Exception){
            Log.e("Get_All_Staff","Error while getting all staff : $e")
            val response = UpdateStaffStatusResponse()
            response.status = Status.FAILURE
            return response
        }
    }
}