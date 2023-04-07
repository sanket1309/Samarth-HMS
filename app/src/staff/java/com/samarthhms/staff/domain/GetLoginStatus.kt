package com.samarthhms.staff.domain

import android.util.Log
import com.samarthhms.staff.constants.LoggedState
import com.samarthhms.staff.constants.Role
import com.samarthhms.staff.repository.StaffStatusRepositoryImpl
import com.samarthhms.staff.repository.StoredStateRepositoryImpl
import com.samarthhms.staff.usecase.UseCase
import com.samarthhms.staff.utils.DateTimeUtils
import javax.inject.Inject

class GetLoginStatus
@Inject constructor(private val storedStateRepository: StoredStateRepositoryImpl, private val staffStatusRepository: StaffStatusRepositoryImpl) : UseCase<LoginStatusResponse, UseCase.None>(){

    private val STAFF_LOGGED_IN_LIMIT_IN_HOURS = 24

    private val ADMIN_LOGGED_IN_LIMIT_IN_HOURS = 24

    override suspend fun run(params: None): LoginStatusResponse {
        return try {
            val storedStateData = storedStateRepository.getStoredState()
            val response = LoginStatusResponse()
            response.role = storedStateData.role
            response.loggedState = storedStateData.loggedState
            if(storedStateData.role == Role.STAFF && DateTimeUtils.getHoursFrom(storedStateData.logInTime!!) >= STAFF_LOGGED_IN_LIMIT_IN_HOURS)
                response.loggedState = LoggedState.LOGGED_OUT
            else if(storedStateData.role == Role.ADMIN && DateTimeUtils.getHoursFrom(storedStateData.logInTime!!) >= ADMIN_LOGGED_IN_LIMIT_IN_HOURS)
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