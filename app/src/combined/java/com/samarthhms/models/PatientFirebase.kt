package com.samarthhms.models

import com.google.firebase.Timestamp
import com.samarthhms.constants.Gender

data class PatientFirebase(
    val patientId: String = "",
    val firstName: String = "",
    val middleName: String = "",
    val lastName: String = "",
    val gender: Gender = Gender.MALE,
    val contactNumber: String = "",
    val dateOfBirth: Timestamp = Timestamp.now(),
    val town: String = "",
    val taluka: String = "",
    val district: String = ""
)