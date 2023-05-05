package com.samarthhms.domain

import com.samarthhms.models.*

data class GetRecentBillsResponse (
    var bills: List<Bill> = listOf(),
    var status: Status = Status.NONE
)