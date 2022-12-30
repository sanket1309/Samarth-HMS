package com.example.samarthhms.repository

import com.example.samarthhms.models.Credentials

interface LoginRepository {
    suspend fun verifyCredentials(credentials: Credentials): Credentials?
    suspend fun getCredentials(id: String): Credentials?
    suspend fun setCredentials(credentials: Credentials)
}