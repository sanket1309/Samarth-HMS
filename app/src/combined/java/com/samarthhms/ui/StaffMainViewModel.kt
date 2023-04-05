package com.samarthhms.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.samarthhms.domain.*
import com.samarthhms.models.Credentials
import com.samarthhms.usecase.UseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StaffMainViewModel @Inject constructor(private val logoutUser: LogoutUser) : ViewModel(){
    private val _logoutUserStatus = MutableLiveData(Status.NONE)
    val logoutUserStatus : LiveData<Status> = _logoutUserStatus

    fun logout(){
        logoutUser(UseCase.None()){
            onLogoutResponse(it)
        }
    }

    private fun onLogoutResponse(logoutResponse: LogoutResponse){
        _logoutUserStatus.value = logoutResponse.logoutResponseStatus
    }
}