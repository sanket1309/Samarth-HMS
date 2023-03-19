package com.samarthhms.domain

import com.samarthhms.models.Admin
import com.samarthhms.models.PatientVisitInfo
import com.samarthhms.models.Staff

class GetAllStaffResponse {
    var status: Status? = Status.NONE
    var data: List<Staff>? = null
}