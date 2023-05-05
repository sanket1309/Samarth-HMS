package com.samarthhms.repository

import com.samarthhms.models.*

interface StoredStateRepository {
    suspend fun getStoredState(): StoredStateData
    suspend fun setStoredState(storedStateData: StoredStateData)
    suspend fun removeStoredState()
    suspend fun getAdminState(): AdminState
    suspend fun setAdminState(admin: Admin)
    suspend fun isSwitchStatePresent(): Boolean
    suspend fun getSwitchAdminState(): AdminState
    suspend fun setSwitchAdminState(admin: Admin)
    suspend fun removeSwitchState()
    suspend fun getStaffState(): StaffState
    suspend fun setStaffState(staff: Staff)
    suspend fun getAdminId(): String?
    suspend fun getId(): String?
}