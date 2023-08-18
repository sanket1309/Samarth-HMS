package com.samarthhms.models

import android.os.Parcelable
import com.samarthhms.constants.Constants
import kotlinx.parcelize.Parcelize

@Parcelize
data class MedicineTemplate(
    var templateId: String=Constants.DefaultValues.ID,
    var templateData: String=Constants.DefaultValues.TEMPLATE_DATA
): Parcelable