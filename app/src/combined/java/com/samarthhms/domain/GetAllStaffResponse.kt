package com.samarthhms.domain

import com.samarthhms.models.StaffDetails

class GetAllStaffResponse {
    var status: Status? = Status.NONE
    var data: List<StaffDetails> = listOf()
}