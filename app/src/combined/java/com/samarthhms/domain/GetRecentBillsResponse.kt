package com.samarthhms.domain

import com.samarthhms.models.Bill

data class GetRecentBillsResponse (
    var bills: List<Bill> = listOf(),
    var status: Status = Status.NONE
)