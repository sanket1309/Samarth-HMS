package com.samarthhms.domain

import android.util.Log
import com.samarthhms.models.Converters
import com.samarthhms.models.Patient
import com.samarthhms.models.PatientVisitInfo
import com.samarthhms.models.SwitchAdminData
import com.samarthhms.repository.*
import com.samarthhms.usecase.UseCase
import java.time.LocalDateTime
import javax.inject.Inject

class GetSwitchAdmins
@Inject constructor(private val adminRepository: AdminRepositoryImpl, private val storedStateRepository: StoredStateRepositoryImpl) : UseCase<GetSwitchAdminsResponse, UseCase.None>(){

    override suspend fun run(params: UseCase.None): GetSwitchAdminsResponse {
        return try {
            val response = GetSwitchAdminsResponse()
            val admins = adminRepository.getAllAdmins().toMutableList()
            val userId = storedStateRepository.getId()
            val currentUserId = if(storedStateRepository.isSwitchStatePresent()) storedStateRepository.getSwitchAdminState().adminId else userId
            response.data = admins.map{
                val switchAdmin = SwitchAdminData(it.adminId, it)
                if(it.adminId == currentUserId){
                    switchAdmin.isCurrentUser = true
                }
                if(it.adminId == userId){
                    switchAdmin.isAccountOwner = true
                }
                switchAdmin
            }
            response.status = Status.SUCCESS
            Log.i("Get_Switch_Admins","Fetched switch admins : ${response.data}")
            response
        } catch (e: Exception){
            Log.e("Get_Switch_Admins","Error while switch admin : $e")
            val response = GetSwitchAdminsResponse()
            response.status = Status.FAILURE
            return response
        }
    }
}