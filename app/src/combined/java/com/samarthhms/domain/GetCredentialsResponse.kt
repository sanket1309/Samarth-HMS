package com.samarthhms.domain

import com.samarthhms.models.Admin
import com.samarthhms.models.Credentials
import com.samarthhms.models.PatientVisitInfo

class GetCredentialsResponse {
    var status: Status? = Status.NONE
    var data: Credentials? = null
}