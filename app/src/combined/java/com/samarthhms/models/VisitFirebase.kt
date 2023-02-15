package com.samarthhms.models

import com.google.firebase.Timestamp
import com.samarthhms.constants.Role

data class VisitFirebase(
    val visitId: String,
    val patientId: String,
    val adminId: String,
    val attendantId: String,
    val attendantRole: Role,
    val visitTime: Timestamp,
    val isAttended: Boolean,
    val isAdmitted: Boolean
)