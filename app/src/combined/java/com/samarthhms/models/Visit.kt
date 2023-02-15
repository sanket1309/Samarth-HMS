package com.samarthhms.models

import android.os.Parcelable
import com.samarthhms.constants.Role
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
data class Visit(
    var visitId: String,
    val patientId: String,
    val adminId: String,
    val attendantId: String,
    val attendantRole: Role,
    val visitTime: LocalDateTime,
    val isAttended: Boolean,
    val isAdmitted: Boolean
): Parcelable