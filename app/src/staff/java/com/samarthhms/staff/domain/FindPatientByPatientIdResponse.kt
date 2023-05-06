package com.samarthhms.staff.domain

import com.samarthhms.staff.models.Patient

class FindPatientByPatientIdResponse {
    var status: Status? = Status.NONE
    var data: Patient? = null
}