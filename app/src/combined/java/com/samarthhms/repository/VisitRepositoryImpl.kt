package com.samarthhms.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.samarthhms.constants.SchemaName
import com.samarthhms.models.*
import com.samarthhms.utils.DateTimeUtils
import com.samarthhms.utils.FirestoreUtils
import kotlinx.coroutines.tasks.await
import java.time.LocalDateTime
import javax.inject.Inject

class VisitRepositoryImpl @Inject constructor(): VisitRepository {

    private val db = FirebaseFirestore.getInstance()
    override suspend fun getVisitsByAttendentAfter(attendentId: String, localDateTime: LocalDateTime): List<Visit> {
        val reference = db.collection(SchemaName.VISITS_COLLECTION)
        val query = reference.whereGreaterThanOrEqualTo(SchemaName.VISIT_TIME, DateTimeUtils.getTimestamp(localDateTime))
            .whereEqualTo(SchemaName.ATTENDANT_ID, attendentId).orderBy(SchemaName.VISIT_TIME, Query.Direction.DESCENDING).limit(1)
        try {
            val snapshots = query.get().await()
            if(!snapshots.isEmpty){
                val visits = snapshots.documents.map { FirestoreUtils.toObjectFromSnapshot(it,Visit::class.java) }
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
                val visits = snapshots.documents.map { FirestoreUtils.toObjectFromSnapshot(it,Visit::class.java) }
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

    override suspend fun getVisitsByDate(dateRange: DateRange): List<Visit> {
        val reference = db.collection(SchemaName.VISITS_COLLECTION)
        val query = reference.whereGreaterThanOrEqualTo(SchemaName.VISIT_TIME, DateTimeUtils.getTimestamp(dateRange.startDate!!))
                             .whereLessThanOrEqualTo(SchemaName.VISIT_TIME, DateTimeUtils.getTimestamp(dateRange.endDate!!))
        try {
            val snapshots = query.get().await()
            if(!snapshots.isEmpty){
                val visits = snapshots.documents.map { FirestoreUtils.toObjectFromSnapshot(it,Visit::class.java) }
                Log.i("Visit_Repository_Impl", "Fetched ${visits.size} visits for date [${dateRange.startDate} - ${dateRange.endDate}]")
                return visits
            }
            Log.i("Visit_Repository_Impl", "No visits found for date [${dateRange.startDate} - ${dateRange.endDate}]")
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
            document.set(FirestoreUtils.toJson(visit))
            Log.i("Visit_Repository_Impl", "Successfully added visit : $visit")
        }catch (e: Exception){
            Log.e("Visit_Repository_Impl", "Error while adding visit : $e")
            throw e
        }
    }
}