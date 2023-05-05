package com.samarthhms.staff.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.samarthhms.staff.domain.LoginResponse
import com.samarthhms.staff.domain.LoginStatus
import com.samarthhms.staff.domain.LoginUser
import com.samarthhms.staff.models.Credentials
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