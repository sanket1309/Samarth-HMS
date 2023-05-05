package com.samarthhms.domain

import com.samarthhms.models.AdminDetails

class GetAllAdminsResponse {
    var status: Status? = Status.NONE
    var data: List<AdminDetails>? = null
}