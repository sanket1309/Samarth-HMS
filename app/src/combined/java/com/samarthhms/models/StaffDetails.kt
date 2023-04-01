package com.samarthhms.models

import android.os.Parcelable
import com.samarthhms.constants.Gender
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
data class StaffDetails(
    var staffId: String = "",
    val staff: Staff?,
    var staffStatus: StaffStatus?,
    var staffCredentials: Credentials?
): Parcelable