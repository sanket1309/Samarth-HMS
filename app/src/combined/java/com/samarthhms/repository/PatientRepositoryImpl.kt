package com.samarthhms.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.samarthhms.constants.SchemaName
import com.samarthhms.models.Converters
import com.samarthhms.models.Patient
import com.samarthhms.models.PatientFirebase
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class PatientRepositoryImpl @Inject constructor(): PatientRepository {

    private val db = FirebaseFirestore.getInstance()

    override suspend fun getPatient(id: String): Patient? {
        val reference = db.collection(SchemaName.PATIENTS_COLLECTION)
        val document = reference.document(id)
        try {
            val snapshot = document.get().await()
            if(snapshot.exists()){
                val patientFirebase = snapshot.toObject(PatientFirebase::class.java)
                val patient = Converters.convertToPatient(patientFirebase!!)
                Log.i("Patient_Repository_Impl", "Fetched patient for patient id $id : [$patient]")
                return patient
            }
            Log.i("Patient_Repository_Impl", "No patient found for patient id $id")
            return null
        }catch (e: Exception){
            Log.e("Patient_Repository_Impl", "Error while fetching patient : $e")
            throw e
        }
    }

    override suspend fun addPatient(patient: Patient) {
        val reference = db.collection(SchemaName.PATIENTS_COLLECTION)
        val document = reference.document(patient.patientId)
        try {
            val patientFirebase = Converters.convertToPatientFirebase(patient)
            document.set(patientFirebase)
            Log.i("Patient_Repository_Impl", "Successfully added patient : [$patient]")
        }catch (e: Exception){
            Log.e("Patient_Repository_Impl", "Error while adding patient : $e")
            throw e
        }
    }
}