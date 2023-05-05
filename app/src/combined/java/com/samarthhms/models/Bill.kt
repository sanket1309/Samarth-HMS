package com.samarthhms.models

import android.os.Parcelable
import com.samarthhms.constants.Gender
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
data class Bill(
    var patientId: String = "",
    var visitId: String = "",
    var admitId: String = "",
    var billNumber: String = "",
    var firstName: String = "",
    var middleName: String = "",
    var lastName: String = "",
    var gender: Gender = Gender.MALE,
    var age: String = "",
    var contactNumber: String = "",
    var dateOfAdmission: LocalDateTime = LocalDateTime.now(),
    var dateOfDischarge: LocalDateTime = LocalDateTime.now(),
    var address: String = "",
    var diagnosis: String = "",
    var treatmentCharges: List<BillItem> = listOf(),
    var managementCharges: List<BillItem> = listOf(),
    var otherCharges: BillItem = BillItem(),
    var sum: Int = 0,
): Parcelable