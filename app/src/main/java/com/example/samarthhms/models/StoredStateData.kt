package com.example.samarthhms.models

import com.example.samarthhms.constants.LoggedState
import com.example.samarthhms.constants.Role
import java.time.LocalDateTime

data class StoredStateData (
    var role: Role = Role.NONE,
    var loggedState: LoggedState = LoggedState.LOGGED_OUT,
    var id: String? = null,
    var logInTime: LocalDateTime? = null
)