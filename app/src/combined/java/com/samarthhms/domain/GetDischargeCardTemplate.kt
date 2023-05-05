package com.samarthhms.domain

import android.util.Log
import com.samarthhms.models.DischargeCardTemplate
import com.samarthhms.repository.MedicineTemplateRepositoryImpl
import com.samarthhms.repository.PatientHistoryTemplateRepositoryImpl
import com.samarthhms.usecase.UseCase
import javax.inject.Inject

class GetDischargeCardTemplate
@Inject constructor(private val medicineTemplateRepository: MedicineTemplateRepositoryImpl, private val patientHistoryTemplateRepository: PatientHistoryTemplateRepositoryImpl) : UseCase<GetDischargeCardTemplateResponse, UseCase.None>(){

    override suspend fun run(params: None): GetDischargeCardTemplateResponse {
        return try {
            val response = GetDischargeCardTemplateResponse()
            val medicineTemplates = medicineTemplateRepository.getAllTemplates()
            medicineTemplates.sortedBy { it.templateData }
            val patientHistoryTemplates = patientHistoryTemplateRepository.getAllTemplates()
            patientHistoryTemplates.sortedBy { it.templateName }

            response.status = Status.SUCCESS
            response.dischargeCardTemplate = DischargeCardTemplate(medicineTemplates, patientHistoryTemplates)
            Log.i("Get_Discharge_Card_Template","GetDischargeCardTemplateResponse = ${response.dischargeCardTemplate!!.medicineTemplates}")
            response
        } catch (e: Exception){
            Log.e("Get_Discharge_Card_Template","Error while getting discharge card templates : $e")
            val response = GetDischargeCardTemplateResponse()
            response.status = Status.FAILURE
            return response
        }
    }
}