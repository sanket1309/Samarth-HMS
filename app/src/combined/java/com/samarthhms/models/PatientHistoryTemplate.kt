package com.samarthhms.models

import android.os.Parcelable
import com.samarthhms.constants.Constants
import kotlinx.parcelize.Parcelize

@Parcelize
data class PatientHistoryTemplate(
    var templateId: String = Constants.DefaultValues.ID,
    var templateName: String = Constants.DefaultValues.NAME,
    var templateData: String = Constants.DefaultValues.TEMPLATE_DATA
): Parcelable