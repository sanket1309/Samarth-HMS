package com.samarthhms.staff.models

import java.time.LocalDateTime

data class PatientVisitInfo(
    val patient: Patient,
    val visitTime: LocalDateTime
)