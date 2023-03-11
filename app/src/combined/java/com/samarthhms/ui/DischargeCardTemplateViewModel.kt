package com.samarthhms.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.samarthhms.constants.SchemaName
import com.samarthhms.domain.*
import com.samarthhms.models.DischargeCardTemplate
import com.samarthhms.models.MedicineTemplate
import com.samarthhms.models.PatientHistoryTemplate
import com.samarthhms.models.PatientVisitInfo
import com.samarthhms.usecase.UseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DischargeCardTemplateViewModel @Inject constructor(val getDischargeCardTemplate: GetDischargeCardTemplate, val addMedicineTemplate: AddMedicineTemplate,
val addPatientHistoryTemplate: AddPatientHistoryTemplate, val deleteMedicineTemplate: DeleteMedicineTemplate,
val deletePatientHistoryTemplate: DeletePatientHistoryTemplate) : ViewModel() {

    private val _getDischargeCardTemplateStatus: MutableLiveData<Status> = MutableLiveData(Status.NONE)
    val getDischargeCardTemplateStatus : LiveData<Status> = _getDischargeCardTemplateStatus

    private val _dischargeCardTemplate: MutableLiveData<DischargeCardTemplate> = MutableLiveData(DischargeCardTemplate(listOf(), listOf()))
    val dischargeCardTemplate : LiveData<DischargeCardTemplate> = _dischargeCardTemplate

    fun getData(){
        getDischargeCardTemplate(UseCase.None()) {
            _getDischargeCardTemplateStatus.value = it.status
            if(it.dischargeCardTemplate == null){
                _dischargeCardTemplate.value = DischargeCardTemplate(listOf(), listOf())
                return@getDischargeCardTemplate
            }
            _dischargeCardTemplate.value = it.dischargeCardTemplate
        }
    }

    fun addMedicineTemplate(medicineTemplate: MedicineTemplate){
        addMedicineTemplate(medicineTemplate) {
            if(it.status == Status.SUCCESS){
                _getDischargeCardTemplateStatus.value = Status.NONE
                getData()
            }
        }
    }

    fun deleteMedicineTemplate(templateId: String){
        deleteMedicineTemplate(templateId) {
            if(it.status == Status.SUCCESS){
                _getDischargeCardTemplateStatus.value = Status.NONE
                getData()
            }
        }
    }

    fun addPatientHistoryTemplate(patientHistoryTemplate: PatientHistoryTemplate){
        addPatientHistoryTemplate(patientHistoryTemplate) {
            if(it.status == Status.SUCCESS){
                _getDischargeCardTemplateStatus.value = Status.NONE
                getData()
            }
        }
    }

    fun deletePatientHistoryTemplate(templateId: String){
        deletePatientHistoryTemplate(templateId) {
            if(it.status == Status.SUCCESS){
                _getDischargeCardTemplateStatus.value = Status.NONE
                getData()
            }
        }
    }
}