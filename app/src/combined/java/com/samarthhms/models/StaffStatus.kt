package com.samarthhms.models

import android.os.Parcelable
import com.samarthhms.constants.Gender
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
data class StaffStatus(
    var staffId: String = "",
    var isLoggedIn: Boolean = false,
    var isLocked: Boolean = false
): Parcelable