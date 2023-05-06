package com.samarthhms.domain

import android.util.Log
import com.samarthhms.models.MedicineTemplate
import com.samarthhms.repository.MedicineTemplateRepositoryImpl
import com.samarthhms.usecase.UseCase
import javax.inject.Inject

class AddMedicineTemplate
@Inject constructor(private val medicineTemplateRepository: MedicineTemplateRepositoryImpl) : UseCase<AddMedicineTemplateResponse, MedicineTemplate>(){

    override suspend fun run(params: MedicineTemplate): AddMedicineTemplateResponse {
        val addMedicineTemplateResponse = AddMedicineTemplateResponse()
        return try {
            if(params.templateId.isNotBlank()){
                medicineTemplateRepository.updateTemplate(params)
            }
            else{
                medicineTemplateRepository.addTemplate(params)
            }
            Log.i("Add_Medicine_Template","Successfully added new template")
            addMedicineTemplateResponse.status = Status.SUCCESS
            addMedicineTemplateResponse
        }catch (e : Exception){
            Log.e("Add_Medicine_Template","Error while adding new template : $e")
            addMedicineTemplateResponse.status = Status.FAILURE
            addMedicineTemplateResponse
        }
    }
}