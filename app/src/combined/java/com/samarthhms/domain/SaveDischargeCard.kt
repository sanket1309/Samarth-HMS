package com.samarthhms.domain

import android.util.Log
import com.samarthhms.repository.DischargeCardRepositoryImpl
import com.samarthhms.usecase.UseCase
import javax.inject.Inject

class SaveDischargeCard
@Inject constructor(private val dischargeCardRepository: DischargeCardRepositoryImpl) : UseCase<SaveDischargeCardResponse, SaveDischargeCardRequest>(){

    override suspend fun run(params: SaveDischargeCardRequest): SaveDischargeCardResponse {
        return try {
            val response = SaveDischargeCardResponse()
//            val previousIpdNumber = params.previousIpdNumber
            if(params.isNewCard) {
                val doesIpdNumberExists = dischargeCardRepository.checkIpdNumberExists(params.dischargeCard.ipdNumber)
                if(doesIpdNumberExists){
                    Log.i("Save_Discharge_Card","Ipd Number ${params.dischargeCard.ipdNumber} already exists")
                    response.status = Status.IPD_NUMBER_ALREADY_EXISTS
                    return response
                }
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