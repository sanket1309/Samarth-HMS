package com.samarthhms.models

import android.os.Parcelable
import com.samarthhms.constants.Gender
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
data class MedicineTemplate(
    var templateId: String="",
    var templateData: String=""
): Parcelable