package com.samarthhms.domain

import com.samarthhms.models.Admin
import com.samarthhms.models.AdminDetails
import com.samarthhms.models.PatientVisitInfo

class GetAllAdminsResponse {
    var status: Status? = Status.NONE
    var data: List<AdminDetails>? = null
}