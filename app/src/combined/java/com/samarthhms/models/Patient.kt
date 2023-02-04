package com.samarthhms.models

import com.samarthhms.constants.Gender
import java.time.LocalDateTime

data class Patient(
    val patientId: String,
    val firstName: String,
    val middleName: String,
    val lastName: String,
    val gender: Gender,
    val contactNumber: String,
    val dateOfBirth: LocalDateTime,
    val town: String,
    val taluka: String,
    val district: String
)