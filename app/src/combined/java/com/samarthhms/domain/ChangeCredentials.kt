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