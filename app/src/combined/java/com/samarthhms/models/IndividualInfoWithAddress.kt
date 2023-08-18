package com.samarthhms.models

import android.os.Parcelable
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor
import com.samarthhms.constants.Constants
import com.samarthhms.constants.Gender
import com.samarthhms.utils.FirestoreLocalDateTime
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
open class IndividualInfoWithAddress(
    var address: String = Constants.DefaultValues.ADDRESS
): IndividualInfo(), Parcelable