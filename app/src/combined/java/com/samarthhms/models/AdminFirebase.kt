package com.samarthhms.models

import android.os.Parcelable
import com.google.firebase.Timestamp
import com.samarthhms.constants.Gender
import kotlinx.parcelize.Parcelize

@Parcelize
data class AdminFirebase(
    var adminId: String = ""
): IndividualInfo(),Parcelable