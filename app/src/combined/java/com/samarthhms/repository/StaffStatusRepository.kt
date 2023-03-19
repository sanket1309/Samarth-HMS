package com.samarthhms.repository

import com.samarthhms.models.Staff
import com.samarthhms.models.StaffStatus

interface StaffStatusRepository {
    suspend fun getStaffStatus(staffId: String): StaffStatus?
    suspend fun addStaffStatus(staffStatus: StaffStatus)
}