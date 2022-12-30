package com.example.samarthhms.domain

import android.content.Context
import android.util.Log
import com.example.samarthhms.constants.LoggedState
import com.example.samarthhms.constants.StoredState
import com.example.samarthhms.models.Credentials
import com.example.samarthhms.models.StoredStateData
import com.example.samarthhms.repository.LoginRepository
import com.example.samarthhms.repository.StoredStateRepository
import com.example.samarthhms.repository.StoredStateRepositoryImpl
import com.example.samarthhms.usecase.Response
import com.example.samarthhms.usecase.UseCase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.util.Objects
import javax.inject.Inject
import kotlin.math.log

class LoginUser constructor() : UseCase<LoginResponse, Credentials>() {

    @Inject
    lateinit var loginRepository: LoginRepository

//    @Inject
    lateinit var storedStateRepository: StoredStateRepositoryImpl

    constructor(context: Context) : this() {
        storedStateRepository = StoredStateRepositoryImpl(context)
    }

    override suspend fun run(credentials: Credentials): LoginResponse {
        return login(credentials)
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