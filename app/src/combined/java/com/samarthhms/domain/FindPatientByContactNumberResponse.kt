package com.samarthhms.domain

import com.samarthhms.models.Patient

class FindPatientByContactNumberResponse {
    var status: Status? = Status.NONE
    var data: List<Patient>? = null
}