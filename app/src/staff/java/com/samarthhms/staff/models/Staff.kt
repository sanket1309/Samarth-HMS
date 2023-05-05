package com.samarthhms.staff.models

import android.os.Parcelable
import com.samarthhms.staff.constants.Gender
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
data class Staff(
    var adminId: String = "",
    var staffId: String = "",
    val firstName: String = "",
    val middleName: String = "",
    val lastName: String = "",
    val gender: Gender = Gender.MALE,
    val contactNumber: String = "",
    val address: String = ""
): Parcelable