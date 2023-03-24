package com.samarthhms.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.samarthhms.constants.SchemaName
import com.samarthhms.models.Converters
import com.samarthhms.models.PatientFirebase
import com.samarthhms.models.Visit
import com.samarthhms.models.VisitFirebase
import com.samarthhms.repository.VisitRepository
import com.samarthhms.utils.DateTimeUtils
import kotlinx.coroutines.tasks.await
import java.time.LocalDateTime
import javax.inject.Inject

class VisitRepositoryImpl @Inject constructor(): VisitRepository {

    private val db = FirebaseFirestore.getInstance()
    override suspend fun getVisitsOnDate(localDateTime: LocalDateTime): List<Visit> {
        val reference = db.collection(SchemaName.VISITS_COLLECTION)
        val query = reference.whereGreaterThanOrEqualTo(SchemaName.VISIT_TIME, DateTimeUtils.getTimestamp(DateTimeUtils.getStartOfDate(localDateTime)))
        try {
            val snapshots = query.get().await()
            if(!snapshots.isEmpty){
                val visits = snapshots.documents.map { Converters.convertToVisit(it.toObject(
                    VisitFirebase::class.java)!!) }
                Log.i("Visit_Repository_Impl", "Fetched ${visits.size} visits for date $localDateTime")
                return visits
            }
            Log.i("Visit_Repository_Impl", "No visits found for date $localDateTime")
            return listOf()
        }catch (e: Exception){
            Log.e("Visit_Repository_Impl", "Error while fetching visits on date : $e")
            throw e
        }
    }

    override suspend fun getVisitsOnDateByAdmin(adminId: String, localDateTime: LocalDateTime): List<Visit> {
        val reference = db.collection(SchemaName.VISITS_COLLECTION)
        val query = reference.whereEqualTo(SchemaName.ADMIN_ID, adminId).whereGreaterThanOrEqualTo(SchemaName.VISIT_TIME, DateTimeUtils.getTimestamp(DateTimeUtils.getStartOfDate(localDateTime)))
        try {
            val snapshots = query.get().await()
            if(!snapshots.isEmpty){
                val visits = snapshots.documents.map { Converters.convertToVisit(it.toObject(
                    VisitFirebase::class.java)!!) }
                Log.i("Visit_Repository_Impl", "Fetched ${visits.size} visits for date $localDateTime")
                return visits
            }
            Log.i("Visit_Repository_Impl", "No visits found for date $localDateTime")
            return listOf()
        }catch (e: Exception){
            Log.e("Visit_Repository_Impl", "Error while fetching visits on date : $e")
            throw e
        }
    }

    override suspend fun addVisit(visit: Visit) {
        try {
            val reference = db.collection(SchemaName.VISITS_COLLECTION)
            val document = reference.document()
            visit.visitId = document.id
            document.set(Converters.convertToVisitFirebase(visit))
            Log.i("Visit_Repository_Impl", "Successfully added visit : $visit")
        }catch (e: Exception){
            Log.e("Visit_Repository_Impl", "Error while adding visit : $e")
            throw e
        }
    }
}