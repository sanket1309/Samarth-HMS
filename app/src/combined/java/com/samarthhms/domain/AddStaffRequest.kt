package com.samarthhms.domain

import com.samarthhms.models.Credentials
import com.samarthhms.models.Staff

data class AddStaffRequest (
    var staff: Staff = Staff(),
    var credentials: Credentials = Credentials()
)