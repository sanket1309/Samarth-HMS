package com.samarthhms.models

import android.os.Parcelable
import com.samarthhms.constants.Constants
import kotlinx.parcelize.Parcelize

@Parcelize
data class AdminDetails(
    var adminId: String = Constants.DefaultValues.ID,
    val admin: Admin,
    var switchAdminData: SwitchAdminData? = null,
    var adminCredentials: Credentials
): Parcelable