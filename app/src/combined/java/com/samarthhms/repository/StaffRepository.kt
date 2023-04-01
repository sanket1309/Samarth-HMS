package com.samarthhms.repository

import com.samarthhms.models.Staff

interface StaffRepository {
    suspend fun getStaff(staffId: String): Staff?
    suspend fun getAllStaff(adminId: String): List<Staff>
    suspend fun addStaff(staff: Staff):String?
    suspend fun removeStaff(staffId: String)
}