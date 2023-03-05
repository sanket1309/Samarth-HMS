package com.samarthhms.domain

import android.util.Log
import com.samarthhms.constants.LoggedState
import com.samarthhms.constants.Role
import com.samarthhms.models.Credentials
import com.samarthhms.models.StoredStateData
import com.samarthhms.repository.AdminRepositoryImpl
import com.samarthhms.repository.LoginRepositoryImpl
import com.samarthhms.repository.StaffRepositoryImpl
import com.samarthhms.repository.StoredStateRepositoryImpl
import com.samarthhms.usecase.UseCase
import java.time.LocalDateTime
import java.util.Objects
import javax.inject.Inject

class LogoutUser @Inject constructor(private var storedStateRepository: StoredStateRepositoryImpl)
    : UseCase<LogoutResponse, UseCase.None>() {

    override suspend fun run(params: None): LogoutResponse {
        return logout()
    }

    private suspend fun logout() : LogoutResponse {
        val logoutResponse = LogoutResponse()
        try {
            storedStateRepository.removeStoredState()
            Log.i("Logout_User","Successfully logged out user")
            logoutResponse.logoutResponseStatus = Status.SUCCESS
            return logoutResponse
        } catch (e : Exception){
            Log.e("Logout_User","Error while logging out : $e")
            logoutResponse.logoutResponseStatus = Status.FAILURE
            return logoutResponse
        }
    }
}