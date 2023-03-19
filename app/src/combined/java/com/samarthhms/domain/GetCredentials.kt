package com.samarthhms.domain

import android.util.Log
import com.samarthhms.constants.Role
import com.samarthhms.models.Credentials
import com.samarthhms.models.Patient
import com.samarthhms.models.PatientVisitInfo
import com.samarthhms.repository.*
import com.samarthhms.usecase.UseCase
import java.time.LocalDateTime
import javax.inject.Inject
import kotlin.math.log

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