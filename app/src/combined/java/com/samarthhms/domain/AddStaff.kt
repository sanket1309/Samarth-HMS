package com.samarthhms.domain

import android.util.Log
import com.samarthhms.models.StaffStatus
import com.samarthhms.repository.LoginRepositoryImpl
import com.samarthhms.repository.StaffRepositoryImpl
import com.samarthhms.repository.StaffStatusRepositoryImpl
import com.samarthhms.repository.StoredStateRepositoryImpl
import com.samarthhms.usecase.UseCase
import javax.inject.Inject

class AddStaff
@Inject constructor(private val storedStateRepository: StoredStateRepositoryImpl,private val staffRepository: StaffRepositoryImpl, private val staffStatusRepository: StaffStatusRepositoryImpl, private val loginRepository: LoginRepositoryImpl) : UseCase<AddStaffResponse, AddStaffRequest>(){

    override suspend fun run(params: AddStaffRequest): AddStaffResponse {
        return try {
            val response = AddStaffResponse()
            val adminId = if(params.staff.staffId.isNotBlank()){
                params.staff.adminId
            }else{
                if(storedStateRepository.isSwitchStatePresent())
                    storedStateRepository.getSwitchAdminState().adminId!!
                else{
                    storedStateRepository.getAdminId()!!
                }
            }
            params.staff.adminId = adminId
            val staffId = staffRepository.addStaff(params.staff)!!
            val staffStatus = StaffStatus(staffId)
            staffStatusRepository.addStaffStatus(staffStatus)
            params.credentials.id = staffId
            loginRepository.setCredentials(params.credentials)
            response.status = Status.SUCCESS
            Log.i("Add_Staff","AddStaffResponse = $response")
            response
        } catch (e: Exception){
            Log.e("Add_Staff","Error while adding staff : $e")
            val response = AddStaffResponse()
            response.status = Status.FAILURE
            return response
        }
    }
}