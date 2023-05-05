package com.samarthhms.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PatientHistoryTemplate(
    var templateId: String = "",
    var templateName: String = "",
    var templateData: String = ""
): Parcelable