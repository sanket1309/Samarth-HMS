package com.samarthhms.staff.repository

import com.samarthhms.staff.models.Staff
import com.samarthhms.staff.models.StaffStatus

interface StaffStatusRepository {
    suspend fun getStaffStatus(staffId: String): StaffStatus?
    suspend fun addStaffStatus(staffStatus: StaffStatus)
    suspend fun removeStaffStatus(staffId: String)
}