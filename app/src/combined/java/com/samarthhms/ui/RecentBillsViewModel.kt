package com.samarthhms.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.samarthhms.domain.GetRecentBills
import com.samarthhms.domain.Status
import com.samarthhms.models.Bill
import com.samarthhms.usecase.UseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RecentBillsViewModel @Inject constructor(val getRecentBills: GetRecentBills) : ViewModel() {

    private val _getRecentBillsStatus: MutableLiveData<Status> = MutableLiveData(Status.NONE)
//    val getRecentBillsStatus : LiveData<Status> = _getRecentBillsStatus

    private val _recentBillsList: MutableLiveData<List<Bill>> = MutableLiveData(listOf())
    val recentBillsList : LiveData<List<Bill>> = _recentBillsList

    fun updateData(){
        getRecentBills(UseCase.None()) {
            _getRecentBillsStatus.value = it.status
            _recentBillsList.value = it.bills
        }
    }
}