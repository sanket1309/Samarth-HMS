package com.samarthhms.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.samarthhms.constants.SchemaName
import com.samarthhms.models.Bill
import com.samarthhms.models.BillFirebase
import com.samarthhms.models.Converters
import com.samarthhms.utils.StringUtils
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class BillRepositoryImpl @Inject constructor(): BillRepository {

    private val db = FirebaseFirestore.getInstance()

    override suspend fun getBill(billNumber: String): Bill? {
        val reference = db.collection(SchemaName.BILL_COLLECTION)
        try {
            val snapshot = reference.document(StringUtils.formatYearWiseIdForFirebase(billNumber)).get().await()
            if(snapshot.exists()){
                val bill = Converters.convertToBill(snapshot.toObject(BillFirebase::class.java)!!)
                Log.i("BillRepositoryImpl", "Bill found for bill number $billNumber")
                return bill
            }
            Log.i("BillRepositoryImpl", "No bill found for bill number $billNumber")
            return null
        }catch (e: Exception){
            Log.e("BillRepositoryImpl", "Error while fetching bill : $e")
            throw e
        }
    }

    override suspend fun getAllBills(limit: Int): List<Bill> {
        val query = db.collection(SchemaName.BILL_COLLECTION)
            .orderBy(SchemaName.BILL_NUMBER, Query.Direction.DESCENDING)
            .limit(limit.toLong())
        try {
            val snapshots = query.get().await()
            if(!snapshots.isEmpty){
                val bills = snapshots.map { Converters.convertToBill(it.toObject(BillFirebase::class.java)) }
                Log.i("BillRepositoryImpl", "Fetched ${bills.size} recent bills")
                return bills
            }
            Log.i("BillRepositoryImpl", "No bills found")
            return listOf()
        }catch (e: Exception){
            Log.e("BillRepositoryImpl", "Error while fetching all bill : $e")
            throw e
        }
    }

    override suspend fun deleteBill(previousBillNumber: String) {
        try {
            val reference = db.collection(SchemaName.BILL_COLLECTION)
            val document = reference.document(StringUtils.formatYearWiseIdForFirebase(previousBillNumber))
            document.delete()
            Log.i("BillRepositoryImpl", "Successfully deleted bill for billNumber : $previousBillNumber")
        }catch (e: Exception){
            Log.e("BillRepositoryImpl", "Error while deleting bill for billNumber = $previousBillNumber, : $e")
            throw e
        }
    }

    override suspend fun saveBill(bill: Bill) {
        try {
            val reference = db.collection(SchemaName.BILL_COLLECTION)
            val document = reference.document(StringUtils.formatYearWiseIdForFirebase(bill.billNumber))
            document.set(Converters.convertToBillFirebase(bill))
            Log.i("BillRepositoryImpl", "Successfully added bill : $bill")
        }catch (e: Exception){
            Log.e("BillRepositoryImpl", "Error while adding bill : $e")
            throw e
        }
    }

    override suspend fun checkBillNumberExists(billNumber: String): Boolean {
        val reference = db.collection(SchemaName.BILL_COLLECTION)
        try {
            val snapshot = reference.document(StringUtils.formatYearWiseIdForFirebase(billNumber)).get().await()
            if(snapshot.exists()){
                Log.i("BillRepositoryImpl", "bill found for bill number $billNumber")
                return true
            }
            Log.i("BillRepositoryImpl", "No bill found for bill number $billNumber")
            return false
        }catch (e: Exception){
            Log.e("BillRepositoryImpl", "Error while deleting bill : $e")
            throw e
        }
    }
}