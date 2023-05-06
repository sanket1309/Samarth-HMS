package com.samarthhms.staff.domain

import com.samarthhms.staff.models.PatientVisitInfo

class GetRecentVisitResponse {
    var status: Status? = Status.NONE
    var data: List<PatientVisitInfo> = listOf()
}