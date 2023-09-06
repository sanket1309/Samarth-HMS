package com.samarthhms.models

import android.os.Parcelable
import com.google.gson.annotations.JsonAdapter
import com.samarthhms.constants.Constants
import com.samarthhms.utils.LocalDateTimeSerializer
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
open class AdmitPatientInfo(
    var ipdNumber: String = Constants.DefaultValues.ID,
    var admitId: String = Constants.DefaultValues.ID,
    var weightInKg: Float = 0f,
    var heightInCm: Float = 0f,
    @JsonAdapter(LocalDateTimeSerializer::class)
    var dateOfAdmission: LocalDateTime? = null,
    @JsonAdapter(LocalDateTimeSerializer::class)
    var dateOfDischarge: LocalDateTime? = null,
    var diagnosis: String = Constants.DefaultValues.NAME,
): Patient(), Parcelable