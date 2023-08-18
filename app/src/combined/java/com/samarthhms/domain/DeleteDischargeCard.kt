package com.samarthhms.domain

import android.util.Log
import com.samarthhms.repository.DischargeCardRepositoryImpl
import com.samarthhms.usecase.UseCase
import javax.inject.Inject

class DeleteDischargeCard
@Inject constructor(private val dischargeCardRepository: DischargeCardRepositoryImpl) : UseCase<DeleteDischargeCardResponse, String>(){

    override suspend fun run(params: String): DeleteDischargeCardResponse {
        return try {
            val response = DeleteDischargeCardResponse()
            dischargeCardRepository.deleteDischargeCard(params)
            response.status = Status.SUCCESS
            Log.i("Delete_Discharge_Card","DeleteDischargeCardResponse = $response")
            response
        } catch (e: Exception){
            Log.e("Delete_Discharge_Card","Error while deleting discharge card : $e")
            val response = DeleteDischargeCardResponse()
            response.status = Status.FAILURE
            return response
        }
    }
}