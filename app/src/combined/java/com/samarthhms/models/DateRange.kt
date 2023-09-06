package com.samarthhms.models

import android.os.Parcelable
import com.samarthhms.constants.Constants
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

data class DateRange(
    var startDate: LocalDateTime? = null,
    var endDate: LocalDateTime? = null,
)