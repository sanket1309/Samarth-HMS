package com.samarthhms.domain

import com.samarthhms.models.DateRange
import com.samarthhms.models.Name
import com.samarthhms.models.Patient
import com.samarthhms.models.PatientVisitInfo

data class SearchPatientByNameResponse (
    var status: Status = Status.NONE,
    var patientVisitInfoList: List<PatientVisitInfo> = listOf()
)