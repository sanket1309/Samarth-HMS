package com.samarthhms.models

import android.os.Parcelable
import com.samarthhms.constants.Constants
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
open class Admin(
    var adminId: String = Constants.DefaultValues.ID
): IndividualInfoWithAddress(),Parcelable