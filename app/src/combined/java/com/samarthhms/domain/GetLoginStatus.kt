package com.samarthhms.domain

import android.util.Log
import com.samarthhms.constants.LoggedState
import com.samarthhms.constants.Role
import com.samarthhms.repository.LoginRepositoryImpl
import com.samarthhms.repository.StaffStatusRepositoryImpl
import com.samarthhms.repository.StoredStateRepositoryImpl
import com.samarthhms.usecase.UseCase
import com.samarthhms.utils.DateTimeUtils
import javax.inject.Inject

class GetLoginStatus
@Inject constructor(private val storedStateRepository: StoredStateRepositoryImpl, private val staffStatusRepository: StaffStatusRepositoryImpl, val loginRepository: LoginRepositoryImpl) : UseCase<LoginStatusResponse, UseCase.None>(){

    private val staffLoggedInLimitInHours = 24

    private val adminLoggedInLimitInHours = 24

    override suspend fun run(params: None): LoginStatusResponse {
        return try {
            val storedStateData = storedStateRepository.getStoredState()
            val response = LoginStatusResponse()
            if(loginRepository.getCredentials(storedStateData.id!!)==null){
                return response
            }
            response.role = storedStateData.role
            response.loggedState = storedStateData.loggedState
            if(storedStateData.role == Role.STAFF && DateTimeUtils.getHoursFrom(storedStateData.logInTime!!) >= staffLoggedInLimitInHours)
                response.loggedState = LoggedState.LOGGED_OUT
            else if(storedStateData.role == Role.ADMIN && DateTimeUtils.getHoursFrom(storedStateData.logInTime!!) >= adminLoggedInLimitInHours)
                response.loggedState = LoggedState.LOGGED_OUT
            if(response.role == Role.STAFF){
                response.isLocked = staffStatusRepository.getStaffStatus(storedStateData.id!!)!!.isLocked
            }
            Log.i("Get_Login_Status","LoginStatusResponse = $response")
            response
        } catch (e: Exception){
            Log.e("Get_Login_Status","Error while getting login status in : $e")
            LoginStatusResponse()
        }
    }
}