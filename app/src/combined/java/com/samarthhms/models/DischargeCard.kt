package com.samarthhms.models

import android.os.Parcelable
import com.samarthhms.constants.Constants
import com.samarthhms.constants.Gender
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
data class DischargeCard(
    var patientHistory: String? = null,
    var pastHistory: String? = null,
    var familyHistory: String? = null,
    var course: List<String> = listOf(),
    var investigations: String? = null,
    var medicationsOnDischarge: List<String> = listOf(),
    var advice: List<String> = listOf()
): AdmitPatientInfo(),Parcelable