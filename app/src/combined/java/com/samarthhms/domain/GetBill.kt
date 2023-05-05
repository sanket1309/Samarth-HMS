package com.samarthhms.domain

import android.util.Log
import com.samarthhms.models.*
import com.samarthhms.repository.*
import com.samarthhms.service.GenerateBill
import com.samarthhms.service.GenerateDischargeCard
import com.samarthhms.usecase.UseCase
import com.samarthhms.utils.IdUtils
import java.time.LocalDateTime
import javax.inject.Inject

class GetBill
@Inject constructor(private val generateBill: GenerateBill) : UseCase<GetBillResponse, Bill>(){

    override suspend fun run(params: Bill): GetBillResponse {
        val getBillResponse = GetBillResponse()
        return try {
            generateBill.generatePdf(params)
            getBillResponse.file = generateBill.file
            Log.i("Get_Bill","Successfully generated bill")
            getBillResponse.status = Status.SUCCESS
            Log.d("TESTING","Successfully generated bill, response = $getBillResponse")
            getBillResponse
        }catch (e : Exception){
            Log.e("Get_Bill","Error while generating bill : $e")
            Log.e("TESTING","Error while generating bill : $e")
            getBillResponse.status = Status.FAILURE
            getBillResponse
        }
    }
}