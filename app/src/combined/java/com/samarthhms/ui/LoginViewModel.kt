package com.samarthhms.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.samarthhms.domain.LoginResponse
import com.samarthhms.domain.LoginUser
import com.samarthhms.models.Credentials
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginUser: LoginUser) : ViewModel(){
    private val _loginUserResponse = MutableLiveData(LoginResponse())
    val loginUserResponse : LiveData<LoginResponse> = _loginUserResponse

    fun login(credentials: Credentials){
        loginUser(credentials){
            _loginUserResponse.value = it
        }
    }
}