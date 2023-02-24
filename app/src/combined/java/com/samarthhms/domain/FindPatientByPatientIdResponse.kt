package com.samarthhms.domain

import com.samarthhms.models.Patient

class FindPatientByPatientIdResponse {
    var status: Status? = Status.NONE
    var data: Patient? = null
}