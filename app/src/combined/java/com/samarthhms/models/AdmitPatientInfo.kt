package com.samarthhms.models

import android.os.Parcelable
import com.samarthhms.constants.Constants
import com.samarthhms.constants.Gender
import com.samarthhms.utils.FirestoreLocalDateTime
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
open class AdmitPatientInfo(
    var ipdNumber: String = Constants.DefaultValues.ID,
    var admitId: String = Constants.DefaultValues.ID,
    var weightInKg: Float = 0f,
    var heightInCm: Float = 0f,
    @FirestoreLocalDateTime
    var dateOfAdmission: LocalDateTime? = null,
    @FirestoreLocalDateTime
    var dateOfDischarge: LocalDateTime? = null,
    var diagnosis: String = Constants.DefaultValues.NAME,
): Patient(), Parcelable