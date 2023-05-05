package com.samarthhms.domain

import android.util.Log
import com.samarthhms.models.Staff
import com.samarthhms.models.StaffDetails
import com.samarthhms.models.StaffStatus
import com.samarthhms.repository.*
import com.samarthhms.usecase.UseCase
import javax.inject.Inject

class SaveDischargeCard
@Inject constructor(private val dischargeCardRepository: DischargeCardRepositoryImpl) : UseCase<SaveDischargeCardResponse, SaveDischargeCardRequest>(){

    override suspend fun run(params: SaveDischargeCardRequest): SaveDischargeCardResponse {
        return try {
            val response = SaveDischargeCardResponse()
            val previousIpdNumber = params.previousIpdNumber
            if(previousIpdNumber != params.dischargeCard.ipdNumber) {
                dischargeCardRepository.deleteDischargeCard(previousIpdNumber)
            }
            dischargeCardRepository.saveDischargeCard(params.dischargeCard)
            response.status = Status.SUCCESS
            Log.i("Save_Discharge_Card","SaveDischargeCardResponse = $response")
            response
        } catch (e: Exception){
            Log.e("Save_Discharge_Card","Error while save discharge card : $e")
            val response = SaveDischargeCardResponse()
            response.status = Status.FAILURE
            return response
        }
    }
}