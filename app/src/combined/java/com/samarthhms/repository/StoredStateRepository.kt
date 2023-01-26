package com.samarthhms.repository

import com.samarthhms.models.StoredStateData

interface StoredStateRepository {
    suspend fun getStoredState(): StoredStateData
    suspend fun setStoredState(storedStateData: StoredStateData)
    suspend fun getId(): String?
}