package com.samarthhms.repository

import com.samarthhms.models.Visit

interface VisitRepository {
    suspend fun addVisit(visit: Visit)
}