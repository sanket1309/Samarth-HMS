package com.samarthhms.domain

import android.util.Log
import com.samarthhms.models.*
import com.samarthhms.repository.*
import com.samarthhms.usecase.UseCase
import com.samarthhms.utils.IdUtils
import java.time.LocalDateTime
import javax.inject.Inject

class AddPatientHistoryTemplate
@Inject constructor(private val patientHistoryTemplateRepository: PatientHistoryTemplateRepositoryImpl) : UseCase<AddMedicineTemplateResponse, PatientHistoryTemplate>(){

    override suspend fun run(params: PatientHistoryTemplate): AddMedicineTemplateResponse {
        val addMedicineTemplateResponse = AddMedicineTemplateResponse()
        return try {
            if(params.templateId.isNotBlank()){
                patientHistoryTemplateRepository.updateTemplate(params)
            }
            else{
                patientHistoryTemplateRepository.addTemplate(params)
            }
            Log.i("Add_Patient_History_Template","Successfully added new template")
            addMedicineTemplateResponse.status = Status.SUCCESS
            addMedicineTemplateResponse
        }catch (e : Exception){
            Log.e("Add_Patient_History_Template","Error while adding new template : $e")
            addMedicineTemplateResponse.status = Status.FAILURE
            addMedicineTemplateResponse
        }
    }
}