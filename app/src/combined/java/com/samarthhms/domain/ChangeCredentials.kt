package com.samarthhms.domain

import android.util.Log
import com.samarthhms.models.Credentials
import com.samarthhms.repository.LoginRepositoryImpl
import com.samarthhms.repository.StoredStateRepositoryImpl
import com.samarthhms.usecase.UseCase
import javax.inject.Inject

class ChangeCredentials
@Inject constructor(private val loginRepository: LoginRepositoryImpl, private val storedStateRepository: StoredStateRepositoryImpl) : UseCase<ChangeCredentialsResponse, Credentials>(){

    override suspend fun run(params: Credentials): ChangeCredentialsResponse {
        return try {
            val response = ChangeCredentialsResponse()
            loginRepository.setCredentials(params)
            response.status = Status.SUCCESS
            Log.i("Change_Credentials","ChangeCredentialsResponse = $response")
            response
        } catch (e: Exception){
            Log.e("Change_Credentials","Error while changing credentials : $e")
            val response = ChangeCredentialsResponse()
            response.status = Status.FAILURE
            return response
        }
    }
}