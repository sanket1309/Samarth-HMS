package com.samarthhms.domain

import com.samarthhms.models.PatientVisitInfo

class GetPatientsTodayResponse {
    var status: Status? = Status.NONE
    var data: List<PatientVisitInfo>? = null
    var unattendedPatientsCount: Int = 0
}