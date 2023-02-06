package com.samarthhms.repository

import com.samarthhms.models.Patient

interface PatientRepository {
    suspend fun getPatient(id: String): Patient?
    suspend fun addPatient(patient: Patient)
}