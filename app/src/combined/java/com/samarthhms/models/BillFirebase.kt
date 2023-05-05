package com.samarthhms.models

import android.os.Parcelable
import com.google.firebase.Timestamp
import com.samarthhms.constants.Gender
import kotlinx.parcelize.Parcelize

@Parcelize
data class BillFirebase(
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
    var dateOfAdmission: Timestamp = Timestamp.now(),
    var dateOfDischarge: Timestamp = Timestamp.now(),
    var address: String = "",
    var diagnosis: String = "",
    var treatmentCharges: List<BillItem> = listOf(),
    var managementCharges: List<BillItem> = listOf(),
    var otherCharges: BillItem = BillItem(),
    var sum: Int = 0,
): Parcelable