package com.samarthhms.staff.domain

import com.samarthhms.staff.models.Patient

class FindPatientByContactNumberResponse {
    var status: Status? = Status.NONE
    var data: List<Patient>? = null
}