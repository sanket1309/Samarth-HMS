package com.samarthhms.repository

import android.util.Log
import com.samarthhms.constants.SchemaName
import com.google.firebase.firestore.FirebaseFirestore
import com.samarthhms.models.Patient
import com.samarthhms.models.PatientFirebase
import com.samarthhms.utils.DateTimeUtils
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class IdRepositoryImpl @Inject constructor(): IdRepository{

    private val db = FirebaseFirestore.getInstance()

    override suspend fun getCurrentPatientId(): String {
        val reference = db.collection(SchemaName.ID_COLLECTION)
        val document = reference.document(SchemaName.CURRENT_PATIENT_ID)
        try {
            val snapshot = document.get().await()
            if(snapshot.exists()){
                return snapshot.getString(SchemaName.CURRENT_PATIENT_ID_FIELD)!!
            }else{
                throw Exception()
            }
        }catch (e: Exception){
            Log.e("Firestore_Exception","Error while fetching patient id : $e")
            throw e
        }
    }

    override suspend fun setCurrentPatientId(patientId: String) {
        val reference = db.collection(SchemaName.ID_COLLECTION)
        val document = reference.document(SchemaName.CURRENT_PATIENT_ID)
        try {
            document.update(SchemaName.CURRENT_PATIENT_ID_FIELD,patientId)
        }catch (e: Exception){
            Log.e("Firestore_Exception","Error while setting patient id : $e")
            throw e
        }
    }

    override suspend fun getCurrentIpdNumber(): String {
        val reference = db.collection(SchemaName.ID_COLLECTION)
        val document = reference.document(SchemaName.CURRENT_IPD_NUMBER)
        try {
            val snapshot = document.get().await()
            if(snapshot.exists()){
                return snapshot.getString(SchemaName.CURRENT_IPD_NUMBER_FIELD)!!
            }else{
                throw Exception()
            }
        }catch (e: Exception){
            Log.e("Firestore_Exception","Error while fetching IPD number : $e")
            throw e
        }
    }

    override suspend fun setCurrentIpdNumber(ipdNumber: String) {
        val reference = db.collection(SchemaName.ID_COLLECTION)
        val document = reference.document(SchemaName.CURRENT_IPD_NUMBER)
        try {
            document.set(ipdNumber)
        }catch (e: Exception){
            Log.e("Firestore_Exception","Error while setting IPD number : $e")
            throw e
        }
    }

    override suspend fun getCurrentBillNumber(): String {
        val reference = db.collection(SchemaName.ID_COLLECTION)
        val document = reference.document(SchemaName.CURRENT_BILL_NUMBER)
        try {
            val snapshot = document.get().await()
            if(snapshot.exists()){
                return snapshot.getString(SchemaName.CURRENT_BILL_NUMBER_FIELD)!!
            }else{
                throw Exception()
            }
        }catch (e: Exception){
            Log.e("Firestore_Exception","Error while fetching bill number : $e")
            throw e
        }
    }

    override suspend fun setCurrentBillNumber(billNumber: String) {
        val reference = db.collection(SchemaName.ID_COLLECTION)
        val document = reference.document(SchemaName.CURRENT_BILL_NUMBER)
        try {
            document.set(billNumber)
        }catch (e: Exception){
            Log.e("Firestore_Exception","Error while setting bill number : $e")
            throw e
        }
    }
}