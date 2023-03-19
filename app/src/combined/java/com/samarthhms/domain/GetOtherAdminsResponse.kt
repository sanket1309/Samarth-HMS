package com.samarthhms.domain

import com.samarthhms.models.Admin
import com.samarthhms.models.PatientVisitInfo

class GetOtherAdminsResponse {
    var status: Status? = Status.NONE
    var data: List<Admin>? = null
}