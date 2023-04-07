package com.samarthhms.staff.models

import android.os.Parcelable
import com.samarthhms.staff.constants.Role
import kotlinx.parcelize.Parcelize

@Parcelize
data class Credentials(
    var id: String = "def",
    val role: Role = Role.NONE,
    val username: String = "def",
    val password: String = "def"
) : Parcelable