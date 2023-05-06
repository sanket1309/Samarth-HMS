package com.samarthhms.staff.repository

import com.samarthhms.staff.models.Credentials

interface LoginRepository {
    suspend fun verifyCredentials(credentials: Credentials): Credentials?
    suspend fun getCredentials(id: String): Credentials?
    suspend fun setCredentials(credentials: Credentials)
    suspend fun removeCredentials(id: String)
}