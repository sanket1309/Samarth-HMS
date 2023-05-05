package com.samarthhms.models

import android.os.Parcelable
import com.samarthhms.constants.Role
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
data class Visit(
    var visitId: String = "",
    var patientId: String = "",
    var adminId: String = "",
    var attendantId: String = "",
    var attendantRole: Role = Role.STAFF,
    var visitTime: LocalDateTime = LocalDateTime.now(),
    var isAttended: Boolean = false,
    var isAdmitted: Boolean = false
): Parcelable