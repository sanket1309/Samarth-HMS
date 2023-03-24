package com.samarthhms.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.samarthhms.domain.*
import com.samarthhms.models.DischargeCard
import com.samarthhms.models.DischargeCardTemplate
import com.samarthhms.models.SwitchAdminData
import com.samarthhms.usecase.UseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.File
import javax.inject.Inject

@HiltViewModel
class SwitchAdminViewModel
@Inject constructor(val switchAdmin: SwitchAdmin, val getSwitchAdmins: GetSwitchAdmins) : ViewModel() {

    private val _switchAdmins: MutableLiveData<List<SwitchAdminData>> = MutableLiveData(listOf())
    val admins : LiveData<List<SwitchAdminData>> = _switchAdmins

    private val _switchAdminStatus: MutableLiveData<Status> = MutableLiveData(Status.NONE)
    val switchAdminStatus : LiveData<Status> = _switchAdminStatus

    fun getAdmins(){
        getSwitchAdmins(UseCase.None()){
            Log.e("", "Result VIEWMODEL : ${it.data}")
            _switchAdmins.value = it.data
        }
    }

    fun switchAdmin(admin: SwitchAdminData){
        _switchAdminStatus.value = Status.NONE
        switchAdmin(admin.adminId){
            _switchAdminStatus.value = it.status
            getAdmins()
        }
    }
}