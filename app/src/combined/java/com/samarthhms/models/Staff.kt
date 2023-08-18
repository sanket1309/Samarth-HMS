package com.samarthhms.models

import android.os.Parcelable
import com.samarthhms.constants.Constants
import com.samarthhms.constants.Gender
import kotlinx.parcelize.Parcelize

@Parcelize
data class Staff(
    var adminId: String = Constants.DefaultValues.ID
): IndividualInfoWithAddress(),Parcelable