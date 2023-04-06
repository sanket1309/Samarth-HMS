package com.samarthhms.domain

import com.samarthhms.domain.LoginStatus

class LoginResponse {
    var loginResponseStatus: LoginStatus? = LoginStatus.NONE
    var isLocked: Boolean = false
}