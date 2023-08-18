package com.samarthhms.domain

import android.util.Log
import com.samarthhms.repository.BillRepositoryImpl
import com.samarthhms.usecase.UseCase
import javax.inject.Inject

class SaveBill
@Inject constructor(private val billRepository: BillRepositoryImpl) : UseCase<SaveBillResponse, SaveBillRequest>(){

    override suspend fun run(params: SaveBillRequest): SaveBillResponse {
        return try {
            val response = SaveBillResponse()
//            val previousBillNumber = params.previousBillNumber
            if(params.isNewBill){
                val doesBillNumberExists = billRepository.checkBillNumberExists(params.bill.billNumber)
                if(doesBillNumberExists){
                    Log.i("Save_Bill","Bill Number ${params.bill.billNumber} already exists")
                    response.status = Status.BILL_NUMBER_ALREADY_EXISTS
                    return response
                }
            }
            billRepository.saveBill(params.bill)
            response.status = Status.SUCCESS
            Log.i("Save_Bill","SaveBillResponse = $response")
            response
        } catch (e: Exception){
            Log.e("Save_Bill","Error while saving bill : $e")
            val response = SaveBillResponse()
            response.status = Status.FAILURE
            return response
        }
    }
}