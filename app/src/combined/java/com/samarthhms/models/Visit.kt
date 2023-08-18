package com.samarthhms.models

import android.os.Parcelable
import com.samarthhms.constants.Constants
import com.samarthhms.constants.Role
import com.samarthhms.utils.FirestoreLocalDateTime
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
data class Visit(
    var visitId: String = Constants.DefaultValues.ID,
    var patientId: String = Constants.DefaultValues.ID,
    var adminId: String = Constants.DefaultValues.ID,
    var ageInText: String? = Constants.DefaultValues.AGE_TEXT,
    var attendantId: String = Constants.DefaultValues.ID,
    var attendantRole: Role = Role.STAFF,
    @FirestoreLocalDateTime
    var visitTime: LocalDateTime?,
    var isAttended: Boolean = false,
    var isAdmitted: Boolean = false,
    var charges: Int = 0
): Parcelable