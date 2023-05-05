package com.samarthhms.domain

import android.util.Log
import com.samarthhms.repository.BillRepositoryImpl
import com.samarthhms.usecase.UseCase
import javax.inject.Inject

class GetRecentBills
@Inject constructor(private val billRepository: BillRepositoryImpl) : UseCase<GetRecentBillsResponse, UseCase.None>(){

    override suspend fun run(params: None): GetRecentBillsResponse {
        return try {
            val response = GetRecentBillsResponse()
            response.bills = billRepository.getAllBills(10)
            response.status = Status.SUCCESS
            Log.i("Get_Recent_Bills","GetRecentBillsResponse = $response")
            response
        } catch (e: Exception){
            Log.e("Get_Recent_Bills","Error while fetching recent bills : $e")
            val response = GetRecentBillsResponse()
            response.status = Status.FAILURE
            return response
        }
    }
}