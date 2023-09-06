package com.samarthhms.models

import android.os.Parcelable
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor
import com.google.gson.annotations.JsonAdapter
import com.samarthhms.constants.Constants
import com.samarthhms.constants.Gender
import com.samarthhms.utils.LocalDateTimeSerializer
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

open class IndividualInfo(
    var gender: Gender = Constants.DefaultValues.GENDER,
    var contactNumber: String? = null,
    @JsonAdapter(LocalDateTimeSerializer::class)
    var dateOfBirth: LocalDateTime? = null
): Name()