package com.samarthhms.staff.domain

import android.util.Log
import com.samarthhms.staff.constants.Role
import com.samarthhms.staff.models.Patient
import com.samarthhms.staff.models.Visit
import com.samarthhms.staff.repository.StoredStateRepositoryImpl
import com.samarthhms.staff.repository.VisitRepositoryImpl
import com.samarthhms.staff.usecase.UseCase
import java.time.LocalDateTime
import javax.inject.Inject

class AddPatientVisit @Inject constructor(private var visitRepository: VisitRepositoryImpl, private var storedStateRepository: StoredStateRepositoryImpl)
    : UseCase<AddPatientVisitResponse, Patient>() {

    override suspend fun run(params: Patient): AddPatientVisitResponse {
        return addVisit(params)
    }

    private suspend fun addVisit(patient: Patient) : AddPatientVisitResponse {
        val addPatientVisitResponse = AddPatientVisitResponse()
        return try {
            val storedState = storedStateRepository.getStoredState()
            val id = storedStateRepository.getId()
            val adminId = storedStateRepository.getStaffState().adminId
            val visit = Visit(
                "",
                patient.patientId,
                adminId!!,
                id!!,
                storedState.role,
                LocalDateTime.now(),
                isAttended = false,
                isAdmitted = false
            )
            visitRepository.addVisit(visit)
            Log.i("Add_Patient_Visit","Successfully added patient visit")
            addPatientVisitResponse.status = Status.SUCCESS
            addPatientVisitResponse
        }catch (e : Exception){
            Log.e("Add_Patient_Visit","Error while adding patient visit : $e")
            addPatientVisitResponse.status = Status.FAILURE
            addPatientVisitResponse
        }
    }
}