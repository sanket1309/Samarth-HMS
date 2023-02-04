package com.samarthhms.models

import com.samarthhms.constants.Role
import java.time.LocalDateTime

data class Visit(
    val visitId: String,
    val patientId: String,
    val adminId: String,
    val attendantId: String,
    val attendantRole: Role,
    val visitTime: LocalDateTime,
    val isAttended: Boolean,
    val isAdmitted: Boolean
)