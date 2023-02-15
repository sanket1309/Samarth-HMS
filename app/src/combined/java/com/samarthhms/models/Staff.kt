package com.samarthhms.models

import android.os.Parcelable
import com.samarthhms.constants.Gender
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
    val dateOfBirth: LocalDateTime? = LocalDateTime.now(),
    val town: String = "",
    val taluka: String = "",
    val district: String = ""
): Parcelable