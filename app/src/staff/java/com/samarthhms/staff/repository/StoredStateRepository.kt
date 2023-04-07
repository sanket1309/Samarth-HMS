package com.samarthhms.staff.repository

import com.samarthhms.staff.models.*

interface StoredStateRepository {
    suspend fun getStoredState(): StoredStateData
    suspend fun setStoredState(storedStateData: StoredStateData)
    suspend fun removeStoredState()
    suspend fun getStaffState(): StaffState
    suspend fun setStaffState(staff: Staff)
    suspend fun getId(): String?
}