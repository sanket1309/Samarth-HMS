package com.samarthhms.staff.domain

import android.util.Log
import com.samarthhms.staff.constants.Role
import com.samarthhms.staff.repository.*
import com.samarthhms.staff.usecase.UseCase
import javax.inject.Inject

class GetUser
@Inject constructor(private val storedStateRepository: StoredStateRepositoryImpl) : UseCase<GetUserResponse, UseCase.None>(){

    override suspend fun run(params: None): GetUserResponse {
        return try {
            val response = GetUserResponse()
            val role = storedStateRepository.getStoredState().role
            response.userRole = role
            if(role == Role.ADMIN){
//                if(storedStateRepository.isSwitchStatePresent()){
//                    response.admin = storedStateRepository.getSwitchAdminState()
//                }
//                else {
//                    response.admin = storedStateRepository.getAdminState()
//                }
            }else if(role == Role.STAFF){
                response.staff = storedStateRepository.getStaffState()
            }else{
                throw Exception("No user found")
            }
            response.status = Status.SUCCESS
            Log.i("Get_User","Fetched User")
            response
        } catch (e: Exception){
            Log.e("Get_User","Error while fetching user : $e")
            val response = GetUserResponse()
            response.status = Status.FAILURE
            return response
        }
    }
}