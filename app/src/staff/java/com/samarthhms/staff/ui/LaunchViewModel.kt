package com.samarthhms.staff.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.samarthhms.staff.domain.GetLoginStatus
import com.samarthhms.staff.domain.LoginStatusResponse
import com.samarthhms.staff.usecase.UseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LaunchViewModel @Inject constructor(private val getLoginStatus: GetLoginStatus) : ViewModel(){
    private val _loginStatus = MutableLiveData(LoginStatusResponse())
    val loginStatus : LiveData<LoginStatusResponse> = _loginStatus

    fun getLoginStatus(){
        getLoginStatus(UseCase.None()){
            onGetLoginStatus(it)
        }
    }

    private fun onGetLoginStatus(loginStatusResponse: LoginStatusResponse){
        _loginStatus.value = loginStatusResponse
    }
}