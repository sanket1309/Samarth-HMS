package com.samarthhms.domain

import android.util.Log
import com.samarthhms.constants.Role
import com.samarthhms.models.Patient
import com.samarthhms.models.PatientVisitDetails
import com.samarthhms.models.PatientVisitInfo
import com.samarthhms.models.Visit
import com.samarthhms.repository.StoredStateRepositoryImpl
import com.samarthhms.repository.VisitRepositoryImpl
import com.samarthhms.usecase.UseCase
import java.time.LocalDateTime
import javax.inject.Inject

class AddPatientVisit @Inject constructor(private var visitRepository: VisitRepositoryImpl, private var storedStateRepository: StoredStateRepositoryImpl)
    : UseCase<AddPatientVisitResponse, PatientVisitDetails>() {

    override suspend fun run(params: PatientVisitDetails): AddPatientVisitResponse {
        return addVisit(params)
    }

    private suspend fun addVisit(patientVisitDetails: PatientVisitDetails) : AddPatientVisitResponse {
        val addPatientVisitResponse = AddPatientVisitResponse()
        return try {
            val storedState = storedStateRepository.getStoredState()
            val id = if(storedStateRepository.isSwitchStatePresent()) storedStateRepository.getSwitchAdminState().adminId else storedStateRepository.getId()
            val adminId = if(storedState.role == Role.STAFF) storedStateRepository.getStaffState().adminId else id
            val visit = Visit(
                "",
                patientVisitDetails.patient!!.patientId,
                adminId!!,
                patientVisitDetails.patient!!.ageInText,
                id!!,
                storedState.role,
                LocalDateTime.now(),
                isAttended = false,
                isAdmitted = false,
                patientVisitDetails.charges
            )
            visitRepository.addVisit(visit)
            Log.i("Add_Patient_Visit","Successfully added patient visit")
            addPatientVisitResponse.status = Status.SUCCESS
            addPatientVisitResponse
        }catch (e : Exception){
            Log.e("Add_Patient_Visit","Error while adding patient visit : $e",e)
            addPatientVisitResponse.status = Status.FAILURE
            addPatientVisitResponse
        }
    }
}