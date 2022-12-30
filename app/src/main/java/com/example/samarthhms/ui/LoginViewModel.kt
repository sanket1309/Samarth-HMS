package com.example.samarthhms.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.samarthhms.constants.Role
import com.example.samarthhms.domain.LoginResponse
import com.example.samarthhms.domain.LoginResponseStatus
import com.example.samarthhms.domain.LoginUser
import com.example.samarthhms.models.Credentials
import javax.inject.Inject

class LoginViewModel @Inject constructor(private val loginUser: LoginUser) : ViewModel(){
    private val _loginUserStatus = MutableLiveData<LoginResponseStatus>(LoginResponseStatus.NONE)
    val loginUserStatus : LiveData<LoginResponseStatus> = _loginUserStatus

    fun login(credentials: Credentials){
        loginUser(credentials){
            onLoginResponse(it)
        }
    }

    private fun onLoginResponse(loginResponse: LoginResponse){
        _loginUserStatus.value = loginResponse.loginResponseStatus
    }
}