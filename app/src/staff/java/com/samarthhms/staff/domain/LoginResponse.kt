package com.samarthhms.staff.domain

import com.samarthhms.staff.domain.LoginStatus

class LoginResponse {
    var loginResponseStatus: LoginStatus? = LoginStatus.NONE
    var isLocked: Boolean = false
}