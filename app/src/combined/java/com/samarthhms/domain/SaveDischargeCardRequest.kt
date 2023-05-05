package com.samarthhms.domain

import com.samarthhms.models.*

data class SaveDischargeCardRequest (
    var previousIpdNumber: String = "",
    var dischargeCard: DischargeCard = DischargeCard()
)