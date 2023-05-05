package com.samarthhms.domain

import android.util.Log
import com.samarthhms.models.AdminDetails
import com.samarthhms.models.SwitchAdminData
import com.samarthhms.repository.AdminRepositoryImpl
import com.samarthhms.repository.LoginRepositoryImpl
import com.samarthhms.repository.StoredStateRepositoryImpl
import com.samarthhms.usecase.UseCase
import javax.inject.Inject

class GetAllAdmins
@Inject constructor(private val storedStateRepository: StoredStateRepositoryImpl, private val adminRepository: AdminRepositoryImpl, private val loginRepository: LoginRepositoryImpl) : UseCase<GetAllAdminsResponse, UseCase.None>(){

    override suspend fun run(params: None): GetAllAdminsResponse {
        return try {
            val response = GetAllAdminsResponse()
            val adminDetails = mutableListOf<AdminDetails>()
            val admins = adminRepository.getAllAdmins().toMutableList()
            val adminId = storedStateRepository.getAdminId()
            val currentAdminId = if(storedStateRepository.isSwitchStatePresent()) storedStateRepository.getSwitchAdminState().adminId else adminId
            admins.map{
                val credentials = loginRepository.getCredentials(it.adminId)!!
                val isCurrentUser = it.adminId.equals(currentAdminId)
                val isAccountOwner = it.adminId.equals(adminId)
                val switchAdminData = SwitchAdminData(it.adminId, null, isCurrentUser, isAccountOwner)
                val adminDetail = AdminDetails(it.adminId, it, switchAdminData, credentials)
                adminDetails.add(adminDetail)
            }
            response.status = Status.SUCCESS
            response.data = adminDetails
            Log.i("Get_All_Admins","GetAllAdminsResponse = $response")
            response
        } catch (e: Exception){
            Log.e("Get_All_Admins","Error while getting all admins : $e")
            val response = GetAllAdminsResponse()
            response.status = Status.FAILURE
            return response
        }
    }
}