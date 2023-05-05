package com.samarthhms.domain

import android.util.Log
import com.samarthhms.repository.StoredStateRepositoryImpl
import com.samarthhms.usecase.UseCase
import javax.inject.Inject

class LogoutUser @Inject constructor(private var storedStateRepository: StoredStateRepositoryImpl)
    : UseCase<LogoutResponse, UseCase.None>() {

    override suspend fun run(params: None): LogoutResponse {
        return logout()
    }

    private suspend fun logout() : LogoutResponse {
        val logoutResponse = LogoutResponse()
        return try {
            storedStateRepository.removeSwitchState()
            storedStateRepository.removeStoredState()
            Log.i("Logout_User","Successfully logged out user")
            logoutResponse.logoutResponseStatus = Status.SUCCESS
            logoutResponse
        } catch (e : Exception){
            Log.e("Logout_User","Error while logging out : $e")
            logoutResponse.logoutResponseStatus = Status.FAILURE
            logoutResponse
        }
    }
}