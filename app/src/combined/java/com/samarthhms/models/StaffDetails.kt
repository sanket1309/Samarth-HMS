package com.samarthhms.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class StaffDetails(
    var staffId: String = "",
    val staff: Staff?,
    var staffStatus: StaffStatus?,
    var staffCredentials: Credentials?
): Parcelable