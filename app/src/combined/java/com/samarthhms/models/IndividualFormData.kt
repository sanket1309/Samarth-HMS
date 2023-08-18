package com.samarthhms.models

import android.os.Parcelable
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor
import com.samarthhms.constants.Constants
import com.samarthhms.constants.Gender
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

open class IndividualFormData<T>(
    var data: T? = null,
    var credentials: Credentials? = null
)