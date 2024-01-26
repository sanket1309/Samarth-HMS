package com.samarthhms.domain

import android.util.Log
import com.samarthhms.repository.DischargeCardRepositoryImpl
import com.samarthhms.usecase.UseCase
import javax.inject.Inject

class GetRecentDischargeCards
@Inject constructor(private val dischargeCardRepository: DischargeCardRepositoryImpl) : UseCase<GetRecentDischargeCardsResponse, UseCase.None>(){

    override suspend fun run(params: None): GetRecentDischargeCardsResponse {
        return try {
            val response = GetRecentDischargeCardsResponse()
            response.dischargeCards = dischargeCardRepository.getAllDischargeCards(50)
            response.status = Status.SUCCESS
            Log.i("Get_Recent_Discharge_Cards","GetRecentDischargeCardsResponse = $response")
            response
        } catch (e: Exception){
            Log.e("Get_Recent_Discharge_Cards","Error while fetching recent discharge cards : $e")
            val response = GetRecentDischargeCardsResponse()
            response.status = Status.FAILURE
            return response
        }
    }
}