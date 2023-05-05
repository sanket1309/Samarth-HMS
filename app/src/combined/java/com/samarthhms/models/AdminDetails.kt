package com.samarthhms.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AdminDetails(
    var adminId: String = "",
    val admin: Admin,
    var switchAdminData: SwitchAdminData?,
    var adminCredentials: Credentials
): Parcelable