package com.samarthhms.staff.repository


interface IdRepository {
    suspend fun getCurrentPatientId(): String
    suspend fun updateCurrentPatientId(patientId: String)
    suspend fun getCurrentIpdNumber(): String
    suspend fun updateCurrentIpdNumber(ipdNumber: String)
    suspend fun getCurrentBillNumber(): String
    suspend fun updateCurrentBillNumber(billNumber: String)
}