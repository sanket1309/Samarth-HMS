package com.samarthhms.models

import android.os.Parcelable
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor
import com.samarthhms.constants.Constants
import com.samarthhms.constants.Gender

import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

open class Name(
    open var firstName: String = Constants.DefaultValues.NAME,
    open var middleName: String? = null,
    open var lastName: String = Constants.DefaultValues.NAME
)