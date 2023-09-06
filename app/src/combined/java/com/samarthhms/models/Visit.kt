package com.samarthhms.models

import android.os.Parcelable
import com.google.firebase.Timestamp
import com.google.firebase.firestore.Exclude
import com.google.gson.annotations.JsonAdapter
import com.samarthhms.constants.Constants
import com.samarthhms.constants.Role
import com.samarthhms.utils.DateTimeUtils
import com.samarthhms.utils.LocalDateTimeSerializer
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
data class Visit(
    var visitId: String = Constants.DefaultValues.ID,
    var patientId: String = Constants.DefaultValues.ID,
    var adminId: String = Constants.DefaultValues.ID,
    var ageInText: String? = Constants.DefaultValues.AGE_TEXT,
    var attendantId: String = Constants.DefaultValues.ID,
    var attendantRole: Role = Role.STAFF,
    @JsonAdapter(LocalDateTimeSerializer::class)
    var visitTime: LocalDateTime?,
    var isAttended: Boolean = false,
    var isAdmitted: Boolean = false,
    var charges: Int? = 0
): Parcelable{
    @Exclude
    fun getFirebaseTimestamp(): Timestamp? {
        return if (visitTime != null) { DateTimeUtils.getTimestamp(visitTime!!) } else null
    }

    // Custom deserialization from Firebase Timestamp to LocalDateTime
    @Exclude
    fun setFirebaseTimestamp(timestamp: Timestamp?) {
        this.visitTime = if(timestamp == null) null else DateTimeUtils.getLocalDateTime(timestamp)
    }
}