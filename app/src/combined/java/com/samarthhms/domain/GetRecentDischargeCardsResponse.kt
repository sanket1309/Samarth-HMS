package com.samarthhms.domain

import com.samarthhms.models.*

data class GetRecentDischargeCardsResponse (
    var dischargeCards: List<DischargeCard> = listOf(),
    var status: Status = Status.NONE
)