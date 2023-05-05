package com.samarthhms.staff.domain

import com.samarthhms.staff.constants.Role
import com.samarthhms.staff.models.Admin
import com.samarthhms.staff.models.AdminState
import com.samarthhms.staff.models.Staff
import com.samarthhms.staff.models.StaffState

class GetUserResponse {
    var status: Status? = Status.NONE
    var userRole: Role = Role.NONE
    var admin: AdminState? = null
    var staff: StaffState? = null
}