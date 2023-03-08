package com.samarthhms.domain

import android.util.Log
import com.samarthhms.models.*
import com.samarthhms.repository.*
import com.samarthhms.usecase.UseCase
import com.samarthhms.utils.IdUtils
import java.time.LocalDateTime
import javax.inject.Inject

class DeleteMedicineTemplate
@Inject constructor(private val medicineTemplateRepository: MedicineTemplateRepositoryImpl) : UseCase<DeleteMedicineTemplateResponse, String>(){

    override suspend fun run(params: String): DeleteMedicineTemplateResponse {
        val deleteMedicineTemplateResponse = DeleteMedicineTemplateResponse()
        return try {
            if(params.isNotBlank()){
                medicineTemplateRepository.deleteTemplate(params)
            }
            Log.i("Delete_Medicine_Template","Successfully deleted template")
            deleteMedicineTemplateResponse.status = Status.SUCCESS
            deleteMedicineTemplateResponse
        }catch (e : Exception){
            Log.e("Delete_Medicine_Template","Error while deleting template : $e")
            deleteMedicineTemplateResponse.status = Status.FAILURE
            deleteMedicineTemplateResponse
        }
    }
}