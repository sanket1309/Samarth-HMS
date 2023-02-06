package com.samarthhms.repository

import android.util.Log
import com.samarthhms.constants.SchemaName
import com.google.firebase.firestore.FirebaseFirestore
import com.samarthhms.models.Patient
import com.samarthhms.models.PatientFirebase
import com.samarthhms.utils.DateTimeUtils
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class PatientRepositoryImpl @Inject constructor(): PatientRepository{

    private val db = FirebaseFirestore.getInstance()

    override suspend fun getPatient(id: String): Patient? {
        val reference = db.collection(SchemaName.PATIENTS_COLLECTION)
        val document = reference.document(id)
        try {
            val snapshot = document.get().await()
            if(snapshot.exists()){
                val patientFirebase = snapshot.toObject(PatientFirebase::class.java)
                return convertToPatient(patientFirebase!!)
            }
        }catch (e: Exception){
            Log.e("Firestore_Exception","Error while fetching patient : $e")
            throw e
        }
        return null
    }

    override suspend fun addPatient(patient: Patient) {
        val reference = db.collection(SchemaName.PATIENTS_COLLECTION)
        val document = reference.document(patient.patientId)
        try {
            document.set(convertToPatientFirebase(patient))
        }catch (e: Exception){
            Log.e("Firestore_Exception","Error while adding patient : $e")
            throw e
        }
    }

    private fun convertToPatient(patientFirebase: PatientFirebase): Patient{
        return Patient(
            patientFirebase.patientId,
            patientFirebase.firstName,
            patientFirebase.middleName,
            patientFirebase.lastName,
            patientFirebase.gender,
            patientFirebase.contactNumber,
            DateTimeUtils.getLocalDateTime(patientFirebase.dateOfBirth),
            patientFirebase.town,
            patientFirebase.taluka,
            patientFirebase.district
        )
    }

    private fun convertToPatientFirebase(patient: Patient): PatientFirebase{
        return PatientFirebase(
            patient.patientId,
            patient.firstName,
            patient.middleName,
            patient.lastName,
            patient.gender,
            patient.contactNumber,
            DateTimeUtils.getTimestamp(patient.dateOfBirth),
            patient.town,
            patient.taluka,
            patient.district
        )
    }
}