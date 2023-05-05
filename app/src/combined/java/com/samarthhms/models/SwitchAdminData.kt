package com.samarthhms.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SwitchAdminData(
    var adminId: String = "",
    var admin: Admin? = null,
    var isCurrentUser: Boolean = false,
    var isAccountOwner: Boolean = false
): Parcelable