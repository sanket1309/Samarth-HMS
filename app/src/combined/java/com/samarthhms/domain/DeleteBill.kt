package com.samarthhms.domain

import android.util.Log
import com.samarthhms.repository.DischargeCardRepositoryImpl
import com.samarthhms.usecase.UseCase
import javax.inject.Inject

class DeleteBill
@Inject constructor(private val dischargeCardRepository: DischargeCardRepositoryImpl) : UseCase<DeleteBillResponse, String>(){

    override suspend fun run(params: String): DeleteBillResponse {
        return try {
            val response = DeleteBillResponse()
            dischargeCardRepository.deleteDischargeCard(params)
            response.status = Status.SUCCESS
            Log.i("Delete_Bill","DeleteBillResponse = $response")
            response
        } catch (e: Exception){
            Log.e("Delete_Bill","Error while deleting bill : $e")
            val response = DeleteBillResponse()
            response.status = Status.FAILURE
            return response
        }
    }
}