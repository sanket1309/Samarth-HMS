package com.samarthhms.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.samarthhms.domain.GetBill
import com.samarthhms.domain.GetDischargeCard
import com.samarthhms.domain.GetDischargeCardTemplate
import com.samarthhms.domain.Status
import com.samarthhms.models.DischargeCard
import com.samarthhms.models.Bill
import com.samarthhms.models.DischargeCardTemplate
import com.samarthhms.usecase.UseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.File
import javax.inject.Inject

@HiltViewModel
class GenerateBillViewModel @Inject constructor(val getBill: GetBill) : ViewModel() {

    private val _getBillStatus: MutableLiveData<Status> = MutableLiveData(Status.NONE)
    val getBillStatus : LiveData<Status> = _getBillStatus

    private val _billFile: MutableLiveData<File?> = MutableLiveData(null)
    val billFile : LiveData<File?> = _billFile

    fun makeBill(bill: Bill){
        _getBillStatus.value = Status.NONE
        getBill(bill){
            _getBillStatus.value = it.status
            _billFile.value = it.file
        }
    }
}