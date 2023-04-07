package com.samarthhms.staff.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.samarthhms.staff.constants.SchemaName
import com.samarthhms.staff.models.Converters
import com.samarthhms.staff.models.Patient
import com.samarthhms.staff.models.PatientFirebase
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class PatientRepositoryImpl @Inject constructor(): PatientRepository {

    private val db = FirebaseFirestore.getInstance()

    override suspend fun findPatientByPatientId(id: String): Patient? {
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
            Log.e("Patient_Repository_Impl", "Error while fetching patient by patient id : $e")
            throw e
        }
    }

    override suspend fun findPatientByPatientId(ids: List<String>): List<Patient> {
        val reference = db.collection(SchemaName.PATIENTS_COLLECTION)
        val patientIdChunks = ids.chunked(10)
        try {
            val patients = ArrayList<Patient>()
            for(patientIds in patientIdChunks){
                val query = reference.whereIn(SchemaName.PATIENT_ID, patientIds)
                val snapshots = query.get().await()
                if(!snapshots.isEmpty){
                    patients.addAll(snapshots.map { Converters.convertToPatient(it.toObject(PatientFirebase::class.java)) })
                }
            }
            if(patients.isEmpty()){
                Log.i("Patient_Repository_Impl", "No patient found for patient ids")
            }
            Log.i("Patient_Repository_Impl", "Fetched ${patients.size} patients for patient ids")
            return patients
        }catch (e: Exception){
            Log.e("Patient_Repository_Impl", "Error while fetching patients by patient ids : $e")
            throw e
        }
    }

    override suspend fun findPatientsByContactNumber(contactNumber: String): List<Patient> {
        val reference = db.collection(SchemaName.PATIENTS_COLLECTION)
        val query = reference.whereEqualTo(SchemaName.CONTACT_NUMBER, contactNumber)
        try {
            val snapshots = query.get().await()
            if(!snapshots.isEmpty){
                val patients = snapshots.documents.map { Converters.convertToPatient(it.toObject(PatientFirebase::class.java)!!) }
                Log.i("Patient_Repository_Impl", "Fetched ${patients.size} patients for contact number $contactNumber")
                return patients
            }
            Log.i("Patient_Repository_Impl", "No patients found for contact number $contactNumber")
            return listOf()
        }catch (e: Exception){
            Log.e("Patient_Repository_Impl", "Error while fetching patients by contact number : $e")
            throw e
        }
    }

    override suspend fun findPatientsByName(
        firstName: String?,
        middleName: String?,
        lastName: String?
    ): List<Patient> {
        val reference = db.collection(SchemaName.PATIENTS_COLLECTION)
        var query: Query = reference
        try {
            if(firstName.isNullOrBlank() && middleName.isNullOrBlank() && lastName.isNullOrBlank()){
                throw Exception("All names can't be null or blank")
            }
            if(!firstName.isNullOrBlank()){
                query = query.whereEqualTo(SchemaName.FIRST_NAME, firstName)
            }
            if(!middleName.isNullOrBlank()){
                query = query.whereEqualTo(SchemaName.MIDDLE_NAME, middleName)
            }
            if(!lastName.isNullOrBlank()){
                query = query.whereEqualTo(SchemaName.LAST_NAME, lastName)
            }
            val snapshots = query.get().await()
            if(!snapshots.isEmpty){
                val patients = snapshots.documents.map { Converters.convertToPatient(it.toObject(PatientFirebase::class.java)!!) }
                Log.i("Patient_Repository_Impl", "Fetched ${patients.size} patients for names[$firstName,$middleName,$lastName]")
                return patients
            }
            Log.i("Patient_Repository_Impl", "No patients found for names[$firstName,$middleName,$lastName]")
            return listOf()
        }catch (e: Exception){
            Log.e("Patient_Repository_Impl", "Error while fetching patients by name : $e")
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