package com.samarthhms.repository

import com.samarthhms.models.Admin

interface AdminRepository {
    suspend fun getAdmin(adminId: String): Admin?
    suspend fun addAdmin(admin: Admin)
}