package com.samarthhms.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.samarthhms.domain.GetRecentDischargeCards
import com.samarthhms.domain.Status
import com.samarthhms.models.DischargeCard
import com.samarthhms.usecase.UseCase
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RecentDischargeCardsViewModel @Inject constructor(val getRecentDischargeCards: GetRecentDischargeCards) : ViewModel() {

    private val _getRecentBillsStatus: MutableLiveData<Status> = MutableLiveData(Status.NONE)
    val getRecentBillsStatus : LiveData<Status> = _getRecentBillsStatus

    private val _recentDischargeCardsList: MutableLiveData<List<DischargeCard>> = MutableLiveData(listOf())
    val recentDischargeCardsList : LiveData<List<DischargeCard>> = _recentDischargeCardsList

    fun updateData(){
        getRecentDischargeCards(UseCase.None()) {
            _getRecentBillsStatus.value = it.status
            _recentDischargeCardsList.value = it.dischargeCards
        }
    }
}
