package com.samarthhms.staff.domain

import com.samarthhms.staff.constants.LoggedState
import com.samarthhms.staff.constants.Role

data class LoginStatusResponse(
    var role: Role = Role.NONE,
    var loggedState: LoggedState = LoggedState.LOGGED_OUT,
    var isLocked: Boolean = false
)