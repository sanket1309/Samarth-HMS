package com.samarthhms.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.samarthhms.domain.*
import com.samarthhms.models.DischargeCard
import com.samarthhms.models.Bill
import com.samarthhms.models.DischargeCardTemplate
import com.samarthhms.usecase.UseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.File
import javax.inject.Inject

@HiltViewModel
class GenerateBillViewModel @Inject constructor(val getBill: GetBill, val saveBill: SaveBill) : ViewModel() {

    private val _getBillStatus: MutableLiveData<Status> = MutableLiveData(Status.NONE)
    val getBillStatus : LiveData<Status> = _getBillStatus

    private val _billFile: MutableLiveData<File?> = MutableLiveData(null)
    val billFile : LiveData<File?> = _billFile

    private val _saveBillStatus: MutableLiveData<Status> = MutableLiveData(Status.NONE)
    val saveBillStatus : LiveData<Status> = _saveBillStatus

    fun saveBill(previousBillNumber: String, bill: Bill){
        _saveBillStatus.value = Status.NONE
        val request = SaveBillRequest(previousBillNumber, bill)
        saveBill(request){
            _saveBillStatus.value = it.status
        }
    }

    fun makeBill(bill: Bill){
        _getBillStatus.value = Status.NONE
        getBill(bill){
            _billFile.value = it.file
            _getBillStatus.value = it.status
        }
    }
}