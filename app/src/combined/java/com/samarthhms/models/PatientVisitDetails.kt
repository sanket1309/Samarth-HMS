package com.samarthhms.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
data class PatientVisitDetails(
    var patient: Patient? = Patient(),
    var visitTime: LocalDateTime? = null,
    var ageInText: String? = null,
    var charges: Int? = null
): Parcelable