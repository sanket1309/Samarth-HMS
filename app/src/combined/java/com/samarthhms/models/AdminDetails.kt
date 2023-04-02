package com.samarthhms.models

import android.os.Parcelable
import com.samarthhms.constants.Gender
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
data class AdminDetails(
    var adminId: String = "",
    val admin: Admin,
    var switchAdminData: SwitchAdminData?,
    var adminCredentials: Credentials
): Parcelable