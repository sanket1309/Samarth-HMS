package com.example.samarthhms.repository

import com.example.samarthhms.models.StoredStateData

interface StoredStateRepository {
    fun getStoredState(): StoredStateData
    fun setStoredState(storedStateData: StoredStateData)
    fun getId(): String?
}