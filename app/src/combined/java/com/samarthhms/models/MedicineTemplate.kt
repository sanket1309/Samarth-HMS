package com.samarthhms.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MedicineTemplate(
    var templateId: String="",
    var templateData: String=""
): Parcelable