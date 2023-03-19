package com.samarthhms.domain

import android.util.Log
import com.samarthhms.models.*
import com.samarthhms.repository.*
import com.samarthhms.service.GenerateDischargeCard
import com.samarthhms.usecase.UseCase
import com.samarthhms.utils.IdUtils
import java.time.LocalDateTime
import javax.inject.Inject

class GetDischargeCard
@Inject constructor(private val generateDischargeCard: GenerateDischargeCard) : UseCase<GetDischargeCardResponse, DischargeCard>(){

    override suspend fun run(params: DischargeCard): GetDischargeCardResponse {
        val getDischargeCardResponse = GetDischargeCardResponse()
        return try {
            generateDischargeCard.generatePdf(params)
            getDischargeCardResponse.file = generateDischargeCard.file
            Log.i("Get_Discharge_Card","Successfully generated discharge card")
            getDischargeCardResponse.status = Status.SUCCESS
            getDischargeCardResponse
        }catch (e : Exception){
            Log.e("Get_Discharge_Card","Error while generating discharge card : $e")
            getDischargeCardResponse.status = Status.FAILURE
            getDischargeCardResponse
        }
    }
}