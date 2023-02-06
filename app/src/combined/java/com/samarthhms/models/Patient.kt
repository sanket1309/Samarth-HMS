package com.samarthhms.models

import com.samarthhms.constants.Gender
import java.time.LocalDateTime

data class Patient(
    var patientId: String = "",
    val firstName: String = "",
    val middleName: String = "",
    val lastName: String = "",
    val gender: Gender = Gender.MALE,
    val contactNumber: String = "",
    val dateOfBirth: LocalDateTime = LocalDateTime.now(),
    val town: String = "",
    val taluka: String = "",
    val district: String = ""
)