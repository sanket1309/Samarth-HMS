package com.samarthhms.models

import android.os.Parcelable
import com.samarthhms.constants.Constants
import kotlinx.parcelize.Parcelize

@Parcelize
data class BillItem(
    var itemName: String = Constants.DefaultValues.NAME,
    var rate: Int = 0,
    var quantity: Int = 0,
    var sum: Int = 0
): Parcelable