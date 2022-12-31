package com.example.samarthhms.domain

import android.util.Log
import com.example.samarthhms.constants.LoggedState
import com.example.samarthhms.constants.Role
import com.example.samarthhms.repository.StoredStateRepository
import com.example.samarthhms.usecase.UseCase
import com.example.samarthhms.utils.DateTimeUtils
import javax.inject.Inject

class GetLoginStatus
@Inject constructor(private val storedStateRepository: StoredStateRepository) : UseCase<LoggedState, UseCase.None>(){

    private val STAFF_LOGGED_IN_LIMIT_IN_HOURS = 24

    private val ADMIN_LOGGED_IN_LIMIT_IN_HOURS = 24

    override suspend fun run(params: None): LoggedState {
        return try {
            val storedStateData = storedStateRepository.getStoredState()
            if(storedStateData.role == Role.STAFF && DateTimeUtils.getHoursFrom(storedStateData.logInTime!!) >= STAFF_LOGGED_IN_LIMIT_IN_HOURS)
                LoggedState.LOGGED_OUT
            else if(storedStateData.role == Role.ADMIN && DateTimeUtils.getHoursFrom(storedStateData.logInTime!!) >= ADMIN_LOGGED_IN_LIMIT_IN_HOURS)
                LoggedState.LOGGED_OUT
            else
                storedStateData.loggedState
        } catch (e: Exception){
            Log.e("Get Login Status Error","Error while getting login status in : $e")
            LoggedState.LOGGED_OUT
        }
    }
}