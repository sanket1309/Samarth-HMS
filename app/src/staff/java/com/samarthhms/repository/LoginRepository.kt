package com.samarthhms.repository

import com.samarthhms.models.Credentials

interface LoginRepository {
    suspend fun verifyCredentials(credentials: Credentials): Credentials?
    suspend fun getCredentials(id: String): Credentials?
    suspend fun setCredentials(credentials: Credentials)
}