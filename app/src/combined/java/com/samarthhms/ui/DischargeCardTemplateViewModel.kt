package com.samarthhms.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.samarthhms.constants.SchemaName
import com.samarthhms.domain.AddMedicineTemplate
import com.samarthhms.domain.GetDischargeCardTemplate
import com.samarthhms.domain.GetPatientsToday
import com.samarthhms.domain.Status
import com.samarthhms.models.DischargeCardTemplate
import com.samarthhms.models.MedicineTemplate
import com.samarthhms.models.PatientVisitInfo
import com.samarthhms.usecase.UseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DischargeCardTemplateViewModel @Inject constructor(val getDischargeCardTemplate: GetDischargeCardTemplate, val addMedicineTemplate: AddMedicineTemplate) : ViewModel() {

    private val _getDischargeCardTemplateStatus: MutableLiveData<Status> = MutableLiveData(Status.NONE)
    val getDischargeCardTemplateStatus : LiveData<Status> = _getDischargeCardTemplateStatus

    private val _dischargeCardTemplate: MutableLiveData<DischargeCardTemplate> = MutableLiveData(DischargeCardTemplate(listOf(), listOf()))
    val dischargeCardTemplate : LiveData<DischargeCardTemplate> = _dischargeCardTemplate

    fun getData(){
        getDischargeCardTemplate(UseCase.None()) {
            _getDischargeCardTemplateStatus.value = it.status
            Log.i("CHECK","result\n${it.dischargeCardTemplate?.medicineTemplates}")
            if(it.dischargeCardTemplate == null){
                _dischargeCardTemplate.value = DischargeCardTemplate(listOf(), listOf())
                return@getDischargeCardTemplate
            }
            _dischargeCardTemplate.value = it.dischargeCardTemplate
        }
    }

    fun changeStatus(){
        _getDischargeCardTemplateStatus.value = Status.NONE
    }

    fun addMedicineTemplate(medicineTemplate: MedicineTemplate){
        addMedicineTemplate(medicineTemplate) {
            if(it.status == Status.SUCCESS){
                _getDischargeCardTemplateStatus.value = Status.NONE
                getData()
            }
        }
    }
}