package com.samarthhms.repository

import com.samarthhms.models.DateRange
import com.samarthhms.models.Name
import com.samarthhms.models.Patient

interface PatientRepository {
    suspend fun findPatientByPatientId(id: String): Patient?
    suspend fun findPatientByPatientId(ids: List<String>): List<Patient>
    suspend fun findPatientsByContactNumber(contactNumber: String): List<Patient>
    suspend fun findPatientsByName(firstName: String?,middleName: String?, lastName: String?): List<Patient>
    suspend fun searchPatients(name: Name, contactNumber: String?, town: String?): List<Patient>
    suspend fun searchPatientByPatientId(ids: List<String>, name: Name, contactNumber: String?, town: String?): List<Patient>
    suspend fun addPatient(patient: Patient)
}