package com.samarthhms.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class StaffStatus(
    var staffId: String = "",
    var isLoggedIn: Boolean = false,
    var isLocked: Boolean = false
): Parcelable