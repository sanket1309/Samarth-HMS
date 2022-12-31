package com.example.samarthhms.repository

import com.example.samarthhms.models.StoredStateData

interface StoredStateRepository {
    suspend fun getStoredState(): StoredStateData
    suspend fun setStoredState(storedStateData: StoredStateData)
    suspend fun getId(): String?
}