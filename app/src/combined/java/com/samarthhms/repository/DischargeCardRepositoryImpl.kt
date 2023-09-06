package com.samarthhms.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.samarthhms.constants.SchemaName
import com.samarthhms.models.Converters
import com.samarthhms.models.DischargeCard
import com.samarthhms.models.DischargeCardFirebase
import com.samarthhms.utils.FirestoreUtils
import com.samarthhms.utils.StringUtils
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class DischargeCardRepositoryImpl @Inject constructor(): DischargeCardRepository {

    private val db = FirebaseFirestore.getInstance()

    override suspend fun getDischargeCard(ipdNumber: String): DischargeCard? {
        val reference = db.collection(SchemaName.DISCHARGE_CARD_COLLECTION)
        try {
            val snapshot = reference.document(StringUtils.formatYearWiseIdForFirebase(ipdNumber)).get().await()
            if(snapshot.exists()){
                val dischargeCard = FirestoreUtils.toObjectFromSnapshot(snapshot,DischargeCard::class.java)
                Log.i("DischargeCardRepositoryImpl", "discharge card found for ipd number $ipdNumber")
                return dischargeCard
            }
            Log.i("DischargeCardRepositoryImpl", "No discharge card found for ipd number $ipdNumber")
            return null
        }catch (e: Exception){
            Log.e("DischargeCardRepositoryImpl", "Error while fetching discharge card : $e")
            throw e
        }
    }

    override suspend fun getAllDischargeCards(limit: Int): List<DischargeCard> {
        val query = db.collection(SchemaName.DISCHARGE_CARD_COLLECTION)
            .orderBy(SchemaName.IPD_NUMBER, Query.Direction.DESCENDING)
            .limit(limit.toLong())
        try {
            val snapshots = query.get().await()
            if(!snapshots.isEmpty){
                val dischargeCards = snapshots.map { FirestoreUtils.toObjectFromSnapshot(it, DischargeCard::class.java) }
                Log.i("DischargeCardRepositoryImpl", "Fetched ${dischargeCards.size} recent bills")
                return dischargeCards
            }
            Log.i("DischargeCardRepositoryImpl", "No discharge cards found")
            return listOf()
        }catch (e: Exception){
            Log.e("DischargeCardRepositoryImpl", "Error while fetching all discharge cards : $e")
            throw e
        }
    }

    override suspend fun deleteDischargeCard(previousIpdNumber: String) {
        try {
            val reference = db.collection(SchemaName.DISCHARGE_CARD_COLLECTION)
            val document = reference.document(StringUtils.formatYearWiseIdForFirebase(previousIpdNumber))
            document.delete()
            Log.i("DischargeCardRepositoryImpl", "Successfully deleted discharge card for ipd number: $previousIpdNumber")
        }catch (e: Exception){
            Log.e("DischargeCardRepositoryImpl", "Error while deleting discharge card for ipd number = $previousIpdNumber : $e")
            throw e
        }
    }

    override suspend fun saveDischargeCard(dischargeCard: DischargeCard) {
        try {
            val reference = db.collection(SchemaName.DISCHARGE_CARD_COLLECTION)
            val document = reference.document(StringUtils.formatYearWiseIdForFirebase(dischargeCard.ipdNumber))
            document.set(FirestoreUtils.toJson(dischargeCard))
            Log.i("DischargeCardRepositoryImpl", "Successfully added discharge card : $dischargeCard")
        }catch (e: Exception){
            Log.e("DischargeCardRepositoryImpl", "Error while adding discharge card : $e")
            throw e
        }
    }

    override suspend fun checkIpdNumberExists(ipdNumber: String): Boolean {
        val reference = db.collection(SchemaName.DISCHARGE_CARD_COLLECTION)
        try {
            val snapshot = reference.document(StringUtils.formatYearWiseIdForFirebase(ipdNumber)).get().await()
            if(snapshot.exists()){
                Log.i("DischargeCardRepositoryImpl", "discharge card found for ipd number $ipdNumber")
                return true
            }
            Log.i("DischargeCardRepositoryImpl", "No discharge card found for ipd number $ipdNumber")
            return false
        }catch (e: Exception){
            Log.e("DischargeCardRepositoryImpl", "Error while deleting discharge card : $e")
            throw e
        }
    }
}