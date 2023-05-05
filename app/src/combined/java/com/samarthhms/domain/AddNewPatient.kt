package com.samarthhms.domain

import android.util.Log
import com.samarthhms.models.Patient
import com.samarthhms.repository.IdRepositoryImpl
import com.samarthhms.repository.PatientRepositoryImpl
import com.samarthhms.usecase.UseCase
import com.samarthhms.utils.IdUtils
import javax.inject.Inject

class AddNewPatient @Inject constructor(private var patientRepository: PatientRepositoryImpl, private var idRepository: IdRepositoryImpl)
    : UseCase<AddNewPatientResponse, Patient>() {

    override suspend fun run(params: Patient): AddNewPatientResponse {
        return addPatient(params)
    }

    private suspend fun addPatient(patient: Patient) : AddNewPatientResponse {
        val addNewPatientResponse = AddNewPatientResponse()
        return try {
            val currentPatientId = idRepository.getCurrentPatientId()
            patient.patientId = currentPatientId
            patientRepository.addPatient(patient)
            idRepository.updateCurrentPatientId(IdUtils.incrementPatientId(currentPatientId))
            Log.i("Add_New_Patient","Successfully added new patient")
            addNewPatientResponse.status = Status.SUCCESS
            addNewPatientResponse
        }catch (e : Exception){
            Log.e("Add_New_Patient","Error while adding new patient : $e")
            addNewPatientResponse.status = Status.FAILURE
            addNewPatientResponse
        }
    }
}