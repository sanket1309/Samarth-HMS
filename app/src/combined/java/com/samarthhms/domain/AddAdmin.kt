package com.samarthhms.domain

import android.util.Log
import com.samarthhms.models.*
import com.samarthhms.repository.*
import com.samarthhms.usecase.UseCase
import javax.inject.Inject

class AddAdmin
@Inject constructor(private val storedStateRepository: StoredStateRepositoryImpl, private val adminRepository: AdminRepositoryImpl, private val staffStatusRepository: StaffStatusRepositoryImpl, private val loginRepository: LoginRepositoryImpl) : UseCase<AddAdminResponse, AdminDetails>(){

    override suspend fun run(adminDetails: AdminDetails): AddAdminResponse {
        return try {
            val response = AddAdminResponse()
            val adminId = adminRepository.addAdmin(adminDetails.admin)!!
            adminDetails.adminCredentials.id = adminId
            loginRepository.setCredentials(adminDetails.adminCredentials)
            response.status = Status.SUCCESS
            Log.i("Add_Staff","AddStaffResponse = $response")
            response
        } catch (e: Exception){
            Log.e("Add_Staff","Error while adding staff : $e")
            val response = AddAdminResponse()
            response.status = Status.FAILURE
            return response
        }
    }
}