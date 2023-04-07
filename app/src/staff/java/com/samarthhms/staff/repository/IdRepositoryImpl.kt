package com.samarthhms.staff.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.samarthhms.staff.constants.SchemaName
import com.samarthhms.staff.repository.IdRepository
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class IdRepositoryImpl @Inject constructor(): IdRepository {

    private val db = FirebaseFirestore.getInstance()

    override suspend fun getCurrentPatientId(): String {
        try {
            val reference = db.collection(SchemaName.ID_COLLECTION)
            val document = reference.document(SchemaName.CURRENT_PATIENT_ID)
            val snapshot = document.get().await()
            if(snapshot.exists()){
                val currentPatientId = snapshot.getString(SchemaName.CURRENT_PATIENT_ID_FIELD)!!
                Log.i("Id_Repository_Impl", "Fetched current patient id : $currentPatientId")
                return currentPatientId
            }else{
                throw Exception("No current patient id found")
            }
        }catch (e: Exception){
            Log.e("Id_Repository_Impl", "Error while fetching current patient id : $e")
            throw e
        }
    }

    override suspend fun updateCurrentPatientId(patientId: String) {
        try {
            val reference = db.collection(SchemaName.ID_COLLECTION)
            val document = reference.document(SchemaName.CURRENT_PATIENT_ID)
            document.update(SchemaName.CURRENT_PATIENT_ID_FIELD,patientId)
            Log.i("Id_Repository_Impl", "Updated current patient id to : $patientId")
        }catch (e: Exception){
            Log.e("Id_Repository_Impl", "Error while updating current patient id : $e")
            throw e
        }
    }

    override suspend fun getCurrentIpdNumber(): String {
        try {
            val reference = db.collection(SchemaName.ID_COLLECTION)
            val document = reference.document(SchemaName.CURRENT_IPD_NUMBER)
            val snapshot = document.get().await()
            if(snapshot.exists()){
                val currentIpdNumber = snapshot.getString(SchemaName.CURRENT_IPD_NUMBER_FIELD)!!
                Log.i("Id_Repository_Impl", "Fetched current IPD number : $currentIpdNumber")
                return currentIpdNumber
            }else{
                throw Exception("No current IPD number found")
            }
        }catch (e: Exception){
            Log.e("Id_Repository_Impl", "Error while fetching IPD number : $e")
            throw e
        }
    }

    override suspend fun updateCurrentIpdNumber(ipdNumber: String) {
        try {
            val reference = db.collection(SchemaName.ID_COLLECTION)
            val document = reference.document(SchemaName.CURRENT_IPD_NUMBER)
            document.set(ipdNumber)
            Log.i("Id_Repository_Impl", "Updated current IPD number to : $ipdNumber")
        }catch (e: Exception){
            Log.e("Id_Repository_Impl", "Error while updating IPD number : $e")
            throw e
        }
    }

    override suspend fun getCurrentBillNumber(): String {
        try {
            val reference = db.collection(SchemaName.ID_COLLECTION)
            val document = reference.document(SchemaName.CURRENT_BILL_NUMBER)
            val snapshot = document.get().await()
            if(snapshot.exists()){
                val currentBillNumber = snapshot.getString(SchemaName.CURRENT_BILL_NUMBER_FIELD)!!
                Log.i("Id_Repository_Impl", "Fetched current bill number : $currentBillNumber")
                return currentBillNumber
            }else{
                throw Exception("No current bill number found")
            }
        }catch (e: Exception){
            Log.e("Id_Repository_Impl", "Error while fetching bill number : $e")
            throw e
        }
    }

    override suspend fun updateCurrentBillNumber(billNumber: String) {
        try {
            val reference = db.collection(SchemaName.ID_COLLECTION)
            val document = reference.document(SchemaName.CURRENT_BILL_NUMBER)
            document.set(billNumber)
            Log.i("Id_Repository_Impl", "Updated current bill number to : $billNumber")
        }catch (e: Exception){
            Log.e("Id_Repository_Impl", "Error while setting bill number : $e")
            throw e
        }
    }
}