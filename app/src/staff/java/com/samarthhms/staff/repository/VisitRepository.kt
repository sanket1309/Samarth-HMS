package com.samarthhms.staff.repository

import com.samarthhms.staff.models.Visit
import java.time.LocalDateTime

interface VisitRepository {
    suspend fun getVisitsByAttendentAfter(attendentId: String, localDateTime: LocalDateTime): List<Visit>
    suspend fun getVisitsOnDateByAdmin(adminId: String, localDateTime: LocalDateTime): List<Visit>
    suspend fun addVisit(visit: Visit)
}