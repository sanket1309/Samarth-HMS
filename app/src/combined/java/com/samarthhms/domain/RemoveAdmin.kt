package com.samarthhms.domain

import android.util.Log
import com.samarthhms.models.Staff
import com.samarthhms.models.StaffDetails
import com.samarthhms.models.StaffStatus
import com.samarthhms.repository.*
import com.samarthhms.usecase.UseCase
import javax.inject.Inject

class RemoveAdmin
@Inject constructor(private val adminRepository: AdminRepositoryImpl , private val loginRepository: LoginRepositoryImpl) : UseCase<RemoveAdminResponse, String>(){

    override suspend fun run(adminId: String): RemoveAdminResponse {
        return try {
            val response = RemoveAdminResponse()
            adminRepository.removeAdmin(adminId)
            loginRepository.removeCredentials(adminId)
            response.status = Status.SUCCESS
            Log.i("Remove_Admin","RemoveAdminResponse = $response")
            response
        } catch (e: Exception){
            Log.e("Remove_Admin","Error while removing admin : $e")
            val response = RemoveAdminResponse()
            response.status = Status.FAILURE
            return response
        }
    }
}