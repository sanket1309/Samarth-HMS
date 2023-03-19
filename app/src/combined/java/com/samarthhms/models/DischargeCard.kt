package com.samarthhms.models

import android.os.Parcelable
import com.samarthhms.constants.Gender
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
data class DischargeCard(
    var patientId: String = "",
    var ipdNumber: String = "",
    val firstName: String = "",
    val middleName: String = "",
    val lastName: String = "",
    val gender: Gender = Gender.MALE,
    val contactNumber: String = "",
    val weight: Float = 0f,
    val height: Float = 0f,
    val age: Float = 0f,
    val ageFormat: String? = "",
    val dateOfBirth: LocalDateTime = LocalDateTime.now(),
    val dateOfAdmission: LocalDateTime = LocalDateTime.now(),
    val dateOfDischarge: LocalDateTime = LocalDateTime.now(),
    val address: String = "",
    val diagnosis: String = "",
    val patientHistory: String = "",
    val pastHistory: String = "",
    val familyHistory: String = "",
    val course: List<String> = listOf(),
    val investigations: String = "",
    val medicationsOnDischarge: List<String> = listOf(),
    val advice: List<String> = listOf()
): Parcelable