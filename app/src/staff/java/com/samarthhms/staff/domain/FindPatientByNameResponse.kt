package com.samarthhms.staff.domain

import com.samarthhms.staff.models.Patient

class FindPatientByNameResponse {
    var status: Status? = Status.NONE
    var data: List<Patient>? = null
}