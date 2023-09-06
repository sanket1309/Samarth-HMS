package com.samarthhms.models

import android.os.Parcelable
import com.samarthhms.constants.Constants
import com.samarthhms.constants.Gender
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
open class Patient(
    var patientId: String = Constants.DefaultValues.ID,
    var ageInText: String? = null,
    var address: String = Constants.DefaultValues.ADDRESS
): IndividualInfoWithLocation(), Parcelable