package com.samarthhms.models

import android.os.Parcelable
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor
import com.samarthhms.constants.Constants
import com.samarthhms.constants.Gender
import com.samarthhms.utils.FirestoreLocalDateTime
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

open class IndividualInfoWithLocation(
    override var town: String = Constants.DefaultValues.TOWN,
    override var taluka: String = Constants.DefaultValues.TALUKA,
    override var district: String = Constants.DefaultValues.DISTRICT
): IndividualInfo(),Location