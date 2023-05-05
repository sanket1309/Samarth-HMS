package com.samarthhms.repository

import com.samarthhms.models.DischargeCard

interface DischargeCardRepository {
    suspend fun getDischargeCard(ipdNumber: String): DischargeCard?
    suspend fun getAllDischargeCards(limit: Int): List<DischargeCard>
    suspend fun deleteDischargeCard(previousIpdNumber: String)
    suspend fun saveDischargeCard(dischargeCard: DischargeCard)
}