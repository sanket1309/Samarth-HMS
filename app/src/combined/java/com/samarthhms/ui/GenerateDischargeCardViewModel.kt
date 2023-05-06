package com.samarthhms.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.samarthhms.domain.*
import com.samarthhms.models.DischargeCard
import com.samarthhms.models.DischargeCardTemplate
import com.samarthhms.usecase.UseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.File
import javax.inject.Inject

@HiltViewModel
class GenerateDischargeCardViewModel @Inject constructor(val getDischargeCardTemplate: GetDischargeCardTemplate, val getDischargeCard: GetDischargeCard, val saveDischargeCard: SaveDischargeCard) : ViewModel() {

    private val _getDischargeCardTemplateStatus: MutableLiveData<Status> = MutableLiveData(Status.NONE)
    val getDischargeCardTemplateStatus : LiveData<Status> = _getDischargeCardTemplateStatus

    private val _dischargeCardTemplate: MutableLiveData<DischargeCardTemplate> = MutableLiveData(DischargeCardTemplate(listOf(), listOf()))
    val dischargeCardTemplate : LiveData<DischargeCardTemplate> = _dischargeCardTemplate

    private val _getDischargeCardStatus: MutableLiveData<Status> = MutableLiveData(Status.NONE)
    val getDischargeCardStatus : LiveData<Status> = _getDischargeCardStatus

    private val _dischargeCardFile: MutableLiveData<File?> = MutableLiveData(null)
    val dischargeCardFile : LiveData<File?> = _dischargeCardFile

    private val _saveDischargeCardStatus: MutableLiveData<Status> = MutableLiveData(Status.NONE)
    val saveDischargeCardStatus : LiveData<Status> = _saveDischargeCardStatus

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

    fun makeDischargeCard(dischargeCard: DischargeCard){
        _getDischargeCardStatus.value = Status.NONE
        getDischargeCard(dischargeCard){
            _dischargeCardFile.value = it.file
            _getDischargeCardStatus.value = it.status
        }
    }

    fun saveDischargeCard(previousIpdNumber: String, dischargeCard: DischargeCard){
        _saveDischargeCardStatus.value = Status.NONE
        val request = SaveDischargeCardRequest(previousIpdNumber, dischargeCard)
        saveDischargeCard(request){
            _saveDischargeCardStatus.value = it.status
        }
    }

}