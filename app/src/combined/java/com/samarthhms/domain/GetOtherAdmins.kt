package com.samarthhms.domain

import android.util.Log
import com.samarthhms.repository.AdminRepositoryImpl
import com.samarthhms.repository.StoredStateRepositoryImpl
import com.samarthhms.usecase.UseCase
import javax.inject.Inject

class GetOtherAdmins
@Inject constructor(private val adminRepository: AdminRepositoryImpl, private val storedStateRepository: StoredStateRepositoryImpl) : UseCase<GetOtherAdminsResponse, UseCase.None>(){

    override suspend fun run(params: None): GetOtherAdminsResponse {
        return try {
            val response = GetOtherAdminsResponse()
            val userAdminId = storedStateRepository.getAdminId()
            val admins = adminRepository.getAllAdmins().toMutableList()
            admins.removeIf{ it.adminId == userAdminId }
            response.status = Status.SUCCESS
            response.data = admins
            Log.i("Get_Other_Admins","GetOtherAdminsResponse = $response")
            response
        } catch (e: Exception){
            Log.e("Get_Other_Admins","Error while getting other admins : $e")
            val response = GetOtherAdminsResponse()
            response.status = Status.FAILURE
            return response
        }
    }
}