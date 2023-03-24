package com.samarthhms.domain

import android.util.Log
import com.samarthhms.models.Converters
import com.samarthhms.models.Patient
import com.samarthhms.models.PatientVisitInfo
import com.samarthhms.repository.*
import com.samarthhms.usecase.UseCase
import java.time.LocalDateTime
import javax.inject.Inject

class SwitchAdmin
@Inject constructor(private val adminRepository: AdminRepositoryImpl, private val storedStateRepository: StoredStateRepositoryImpl) : UseCase<SwitchAdminResponse, String>(){

    override suspend fun run(params: String): SwitchAdminResponse {
        return try {
            val response = SwitchAdminResponse()
            val switchAdmin = adminRepository.getAdmin(params)!!
            val userId = storedStateRepository.getId()
            if(switchAdmin.adminId != userId) {
                storedStateRepository.setSwitchAdminState(switchAdmin)
            }
            else{
                storedStateRepository.removeSwitchState()
            }
            response.status = Status.SUCCESS
            Log.i("Switch_Admin","Switched to admin")
            response
        } catch (e: Exception){
            Log.e("Switch_Admin","Error while switch admin : $e")
            val response = SwitchAdminResponse()
            response.status = Status.FAILURE
            return response
        }
    }
}