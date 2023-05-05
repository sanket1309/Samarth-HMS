package com.samarthhms.domain

import com.samarthhms.constants.LoggedState
import com.samarthhms.constants.Role

data class LoginStatusResponse(
    var role: Role = Role.NONE,
    var loggedState: LoggedState = LoggedState.LOGGED_OUT,
    var isLocked: Boolean = false
)