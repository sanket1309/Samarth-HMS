package com.samarthhms.repository

import com.samarthhms.models.DateRange
import com.samarthhms.models.Visit
import java.time.LocalDateTime

interface VisitRepository {
    suspend fun getVisitsByAttendentAfter(attendentId: String, localDateTime: LocalDateTime): List<Visit>
    suspend fun getVisitsOnDateByAdmin(adminId: String, localDateTime: LocalDateTime): List<Visit>
    suspend fun getVisitsByDate(dateRange: DateRange): List<Visit>
    suspend fun addVisit(visit: Visit)
}