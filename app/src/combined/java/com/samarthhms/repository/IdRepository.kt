package com.samarthhms.repository

import com.samarthhms.models.Patient

interface IdRepository {
    suspend fun getCurrentPatientId(): String
    suspend fun setCurrentPatientId(patientId: String)
    suspend fun getCurrentIpdNumber(): String
    suspend fun setCurrentIpdNumber(ipdNumber: String)
    suspend fun getCurrentBillNumber(): String
    suspend fun setCurrentBillNumber(billNumber: String)
}