package com.samarthhms.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.samarthhms.constants.SchemaName
import com.samarthhms.models.Converters
import com.samarthhms.models.Visit
import com.samarthhms.repository.VisitRepository
import javax.inject.Inject

class VisitRepositoryImpl @Inject constructor(): VisitRepository {

    private val db = FirebaseFirestore.getInstance()

    override suspend fun addVisit(visit: Visit) {
        try {
            val reference = db.collection(SchemaName.VISITS_COLLECTION)
            val document = reference.document()
            visit.visitId = document.id
            document.set(Converters.convertToVisitFirebase(visit))
            Log.i("Visit_State_Repository_Impl", "Successfully added visit : $visit")
        }catch (e: Exception){
            Log.e("Visit_State_Repository_Impl", "Error while adding visit : $e")
            throw e
        }
    }
}