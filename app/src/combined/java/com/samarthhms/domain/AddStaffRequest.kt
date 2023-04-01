package com.samarthhms.domain

import com.samarthhms.models.*

data class AddStaffRequest (
    var staff: Staff = Staff(),
    var credentials: Credentials = Credentials()
)