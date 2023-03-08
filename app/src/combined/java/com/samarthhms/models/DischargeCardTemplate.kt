package com.samarthhms.models

import android.os.Parcelable
import com.samarthhms.constants.Gender
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
data class DischargeCardTemplate(
    var medicineTemplates: List<MedicineTemplate>,
    var patientHistoryTemplates: List<PatientHistoryTemplate>
): Parcelable