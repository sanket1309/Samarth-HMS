package com.samarthhms.domain

import android.util.Log
import com.samarthhms.constants.LoggedState
import com.samarthhms.models.Credentials
import com.samarthhms.models.StoredStateData
import com.samarthhms.repository.LoginRepositoryImpl
import com.samarthhms.repository.StoredStateRepositoryImpl
import com.samarthhms.usecase.UseCase
import java.time.LocalDateTime
import java.util.Objects
import javax.inject.Inject

class LoginUser @Inject constructor(private var loginRepository: LoginRepositoryImpl,private var storedStateRepository: StoredStateRepositoryImpl)
    : UseCase<LoginResponse, Credentials>() {

    override suspend fun run(params: Credentials): LoginResponse {
        return login(params)
    }

    private suspend fun login(credentials: Credentials) : LoginResponse{
        val loginResponse = LoginResponse()
        try {
            val originalCredentials = loginRepository.verifyCredentials(credentials)
            if(Objects.nonNull(originalCredentials)){
                val storedStateData = StoredStateData(credentials.role,LoggedState.LOGGED_IN,credentials.id, LocalDateTime.now())
                storedStateRepository.setStoredState(storedStateData)
                loginResponse.loginResponseStatus = LoginResponseStatus.SUCCESS
            }
            else{
                loginResponse.loginResponseStatus = LoginResponseStatus.WRONG_CREDENTIALS
            }
            return loginResponse
        }catch (e : Exception){
            Log.e("Login User Error","Error while logging in : $e")
            loginResponse.loginResponseStatus = LoginResponseStatus.EXCEPTION
            return loginResponse
        }
    }
}