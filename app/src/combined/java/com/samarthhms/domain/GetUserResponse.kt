package com.samarthhms.domain

import com.samarthhms.constants.Role
import com.samarthhms.models.Admin
import com.samarthhms.models.AdminState
import com.samarthhms.models.Staff
import com.samarthhms.models.StaffState

class GetUserResponse {
    var status: Status? = Status.NONE
    var userRole: Role = Role.NONE
    var admin: AdminState? = null
    var staff: StaffState? = null
}