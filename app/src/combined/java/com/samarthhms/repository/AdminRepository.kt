package com.samarthhms.repository

import com.samarthhms.models.Admin

interface AdminRepository {
    suspend fun getAdmin(adminId: String): Admin?
    suspend fun getAllAdmins(): List<Admin>
    suspend fun addAdmin(admin: Admin): String?
    suspend fun removeAdmin(adminId: String)
}