package com.samarthhms.domain

import com.samarthhms.models.SwitchAdminData

class GetSwitchAdminsResponse {
    var status: Status? = Status.NONE
    var data: List<SwitchAdminData> = listOf()
}