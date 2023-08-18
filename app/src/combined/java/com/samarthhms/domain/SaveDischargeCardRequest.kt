package com.samarthhms.domain

import com.samarthhms.models.DischargeCard

data class SaveDischargeCardRequest (
    var previousIpdNumber: String = "",
    var dischargeCard: DischargeCard = DischargeCard(),
    var isNewCard: Boolean = true
)