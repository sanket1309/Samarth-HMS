package com.samarthhms.models

import android.os.Parcelable
import com.samarthhms.constants.Constants
import com.samarthhms.constants.Gender
import com.samarthhms.utils.FirestoreLocalDateTime
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
data class Bill(
    var visitId: String = Constants.DefaultValues.ID,
    var billNumber: String = Constants.DefaultValues.ID,
    var treatmentCharges: List<BillItem>? = null,
    var managementCharges: List<BillItem>? = null,
    var otherCharges: BillItem? = null,
    var sum: Int = 0,
): AdmitPatientInfo(), Parcelable