package com.samarthhms.models

import android.os.Parcelable
import com.samarthhms.constants.Constants
import com.samarthhms.constants.Role
import kotlinx.parcelize.Parcelize

@Parcelize
data class Credentials(
    var id: String = Constants.DefaultValues.ID,
    var role: Role = Role.NONE,
    var username: String = Constants.DefaultValues.NAME,
    var password: String = Constants.DefaultValues.NAME
) : Parcelable