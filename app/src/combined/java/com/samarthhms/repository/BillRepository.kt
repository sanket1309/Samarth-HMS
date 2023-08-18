package com.samarthhms.repository

import com.samarthhms.models.Bill

interface BillRepository {
    suspend fun getBill(billNumber: String): Bill?
    suspend fun getAllBills(limit: Int): List<Bill>
    suspend fun deleteBill(previousBillNumber: String)
    suspend fun saveBill(bill: Bill)
    suspend fun checkBillNumberExists(billNumber: String): Boolean
}