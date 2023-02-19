package com.samarthhms.models

import com.google.firebase.Timestamp
import com.samarthhms.constants.Role

data class VisitFirebase(
    var visitId: String = "",
    var patientId: String = "",
    var adminId: String = "",
    var attendantId: String = "",
    var attendantRole: Role = Role.STAFF,
    var visitTime: Timestamp = Timestamp.now(),
    var isAttended: Boolean = false,
    var isAdmitted: Boolean = false
)