package com.samarthhms.models

import java.time.LocalDateTime

data class PatientVisitInfo(
    val patient: Patient,
    val visitTime: LocalDateTime
)