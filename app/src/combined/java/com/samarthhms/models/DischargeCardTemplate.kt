package com.samarthhms.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DischargeCardTemplate(
    var medicineTemplates: List<MedicineTemplate>,
    var patientHistoryTemplates: List<PatientHistoryTemplate>
): Parcelable