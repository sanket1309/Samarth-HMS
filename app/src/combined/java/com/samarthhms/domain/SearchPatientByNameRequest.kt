package com.samarthhms.domain

import com.samarthhms.models.DateRange
import com.samarthhms.models.Name

data class SearchPatientByNameRequest (
    var name: Name? = null,
    var contactNumber: String? = null,
    var town: String? = null,
    var dateRange: DateRange? = null
)