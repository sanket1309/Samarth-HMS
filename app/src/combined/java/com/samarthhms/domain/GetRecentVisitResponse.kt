package com.samarthhms.domain

import com.samarthhms.models.PatientVisitInfo

class GetRecentVisitResponse {
    var status: Status? = Status.NONE
    var data: List<PatientVisitInfo> = listOf()
}