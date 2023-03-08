package com.samarthhms.domain

import android.util.Log
import com.samarthhms.models.DischargeCardTemplate
import com.samarthhms.models.Patient
import com.samarthhms.models.PatientHistoryTemplate
import com.samarthhms.models.PatientVisitInfo
import com.samarthhms.repository.*
import com.samarthhms.usecase.UseCase
import java.time.LocalDateTime
import javax.inject.Inject

class GetDischargeCardTemplate
@Inject constructor(private val medicineTemplateRepository: MedicineTemplateRepositoryImpl) : UseCase<GetDischargeCardTemplateResponse, UseCase.None>(){

    override suspend fun run(params: None): GetDischargeCardTemplateResponse {
        return try {
            val response = GetDischargeCardTemplateResponse()
            val medicineTemplates = medicineTemplateRepository.getAllTemplates()
            medicineTemplates.sortedBy { it.templateData }
            val patientHistoryTemplates = listOf<PatientHistoryTemplate>()
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