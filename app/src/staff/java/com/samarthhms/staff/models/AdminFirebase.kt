package com.samarthhms.staff.models

import android.os.Parcelable
import com.google.firebase.Timestamp
import com.samarthhms.staff.constants.Gender
import kotlinx.parcelize.Parcelize

@Parcelize
data class AdminFirebase(
    var adminId: String = "",
    val firstName: String = "",
    val middleName: String = "",
    val lastName: String = "",
    val gender: Gender = Gender.MALE,
    val contactNumber: String = "",
    val dateOfBirth: Timestamp? = Timestamp.now(),
    val address: String = ""
): Parcelable