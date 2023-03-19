package com.samarthhms.repository

import com.samarthhms.models.Staff

interface StaffRepository {
    suspend fun getStaff(staffId: String): Staff?
    suspend fun getAllStaff(): List<Staff>
    suspend fun addStaff(staff: Staff)
}