package com.samarthhms.models

import android.os.Parcelable
import com.samarthhms.constants.Gender
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
data class BillItem(
    var itemName: String = "",
    var rate: Int = 0,
    var quantity: Int = 0,
    var sum: Int = 0
): Parcelable