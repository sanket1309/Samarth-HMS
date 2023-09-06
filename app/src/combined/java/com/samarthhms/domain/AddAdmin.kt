package com.samarthhms.domain

import android.util.Log
import com.samarthhms.models.AdminDetails
import com.samarthhms.repository.AdminRepositoryImpl
import com.samarthhms.repository.LoginRepositoryImpl
import com.samarthhms.usecase.UseCase
import javax.inject.Inject

class AddAdmin
@Inject constructor(
    private val adminRepository: AdminRepositoryImpl,
    private val loginRepository: LoginRepositoryImpl
) : UseCase<AddAdminResponse, AdminDetails>(){

    override suspend fun run(params: AdminDetails): AddAdminResponse {
        val response = AddAdminResponse()
        return try {
            val adminId = adminRepository.addAdmin(params.admin)!!
            params.adminCredentials.id = adminId
            loginRepository.setCredentials(params.adminCredentials)
            response.status = Status.SUCCESS
            Log.i("Add_Staff","AddStaffResponse = ${response.status?.name}")
            response
        } catch (e: Exception){
            Log.e("Add_Staff","Error while adding staff : $e")
            response.status = Status.FAILURE
            return response
        }
    }
}