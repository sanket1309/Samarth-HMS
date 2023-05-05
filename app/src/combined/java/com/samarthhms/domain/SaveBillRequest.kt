package com.samarthhms.domain

import com.samarthhms.models.Bill

data class SaveBillRequest (
    var previousBillNumber: String = "",
    var bill: Bill = Bill()
)