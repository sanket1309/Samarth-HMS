package com.samarthhms.models

import android.os.Parcelable
import com.samarthhms.constants.Gender
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
data class SwitchAdminData(
    var adminId: String = "",
    var admin: Admin? = null,
    var isCurrentUser: Boolean = false,
    var isAccountOwner: Boolean = false
): Parcelable