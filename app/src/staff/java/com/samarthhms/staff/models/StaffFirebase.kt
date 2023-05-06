package com.samarthhms.staff.models

import android.os.Parcelable
import com.google.firebase.Timestamp
import com.samarthhms.staff.constants.Gender
import kotlinx.parcelize.Parcelize

@Parcelize
data class StaffFirebase(
    var adminId: String = "",
    var staffId: String = "",
    val firstName: String = "",
    val middleName: String = "",
    val lastName: String = "",
    val gender: Gender = Gender.MALE,
    val contactNumber: String = "",
    val address: String = ""
): Parcelable