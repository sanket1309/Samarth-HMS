package com.example.samarthhms.models

import com.example.samarthhms.constants.LoggedState
import com.example.samarthhms.constants.Role
import java.time.LocalDateTime

data class StoredStateData (
    val role: Role = Role.NONE,
    val loggedState: LoggedState = LoggedState.LOGGED_OUT,
    val id: String? = null,
    val logInTime: LocalDateTime? = null
)