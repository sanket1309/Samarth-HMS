package com.samarthhms.domain

import android.util.Log
import com.samarthhms.repository.PatientHistoryTemplateRepositoryImpl
import com.samarthhms.usecase.UseCase
import javax.inject.Inject

class DeletePatientHistoryTemplate
@Inject constructor(private val patientHistoryTemplateRepository: PatientHistoryTemplateRepositoryImpl) : UseCase<DeletePatientHistoryTemplateResponse, String>(){

    override suspend fun run(params: String): DeletePatientHistoryTemplateResponse {
        val deletePatientHistoryTemplateResponse = DeletePatientHistoryTemplateResponse()
        return try {
            if(params.isNotBlank()){
                patientHistoryTemplateRepository.deleteTemplate(params)
            }
            Log.i("Delete_Patient_History_Template","Successfully deleted template")
            deletePatientHistoryTemplateResponse.status = Status.SUCCESS
            deletePatientHistoryTemplateResponse
        }catch (e : Exception){
            Log.e("Delete_Patient_History_Template","Error while deleting template : $e")
            deletePatientHistoryTemplateResponse.status = Status.FAILURE
            deletePatientHistoryTemplateResponse
        }
    }
}