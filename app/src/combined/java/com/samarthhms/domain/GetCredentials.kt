package com.samarthhms.domain

import android.util.Log
import com.samarthhms.repository.LoginRepositoryImpl
import com.samarthhms.repository.StoredStateRepositoryImpl
import com.samarthhms.usecase.UseCase
import javax.inject.Inject

class GetCredentials
@Inject constructor(private val loginRepository: LoginRepositoryImpl, private val storedStateRepository: StoredStateRepositoryImpl) : UseCase<GetCredentialsResponse, String>(){

    override suspend fun run(params: String): GetCredentialsResponse {
        return try {
            val response = GetCredentialsResponse()
            response.data = loginRepository.getCredentials(params)
            response.status = Status.SUCCESS
            Log.i("Get_Credentials","Fetched Credentials Successfully")
            response
        } catch (e: Exception){
            Log.e("Get_Credentials","Error while fetching credentials : $e")
            val response = GetCredentialsResponse()
            response.status = Status.FAILURE
            return response
        }
    }
}