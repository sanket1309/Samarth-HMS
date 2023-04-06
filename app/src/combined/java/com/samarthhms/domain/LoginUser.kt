package com.samarthhms.domain

import android.util.Log
import com.samarthhms.constants.LoggedState
import com.samarthhms.constants.Role
import com.samarthhms.models.Credentials
import com.samarthhms.models.StoredStateData
import com.samarthhms.repository.*
import com.samarthhms.usecase.UseCase
import java.time.LocalDateTime
import java.util.Objects
import javax.inject.Inject

class LoginUser @Inject constructor(private var loginRepository: LoginRepositoryImpl, private var storedStateRepository: StoredStateRepositoryImpl,
                                    private var adminRepository: AdminRepositoryImpl, private var staffRepository: StaffRepositoryImpl,
                                    private var staffStatusRepository: StaffStatusRepositoryImpl
)
    : UseCase<LoginResponse, Credentials>() {

    override suspend fun run(params: Credentials): LoginResponse {
        return login(params)
    }

    private suspend fun login(credentials: Credentials) : LoginResponse {
        val loginResponse = LoginResponse()
        try {
            val originalCredentials = loginRepository.verifyCredentials(credentials)
            if(Objects.nonNull(originalCredentials)){
                val storedStateData = StoredStateData(credentials.role,LoggedState.LOGGED_IN,originalCredentials?.id, LocalDateTime.now())
                if(storedStateData.role == Role.ADMIN){
                    val admin = adminRepository.getAdmin(storedStateData.id!!)!!
                    storedStateRepository.setAdminState(admin)
                    Log.i("Login_User","Stored admin state")
                }
                else{
                    val staff = staffRepository.getStaff(storedStateData.id!!)!!
                    storedStateRepository.setStaffState(staff)
                    Log.i("Login_User","Stored staff state")
                }
                storedStateRepository.removeSwitchState()
                storedStateRepository.setStoredState(storedStateData)
                if(credentials.role == Role.STAFF){
                    loginResponse.isLocked = staffStatusRepository.getStaffStatus(storedStateRepository.getId()!!)!!.isLocked
                }

                Log.i("Login_User","Successfully logged in user")
                loginResponse.loginResponseStatus = LoginStatus.SUCCESS
            }
            else{
                Log.i("Login_User","Wrong credentials")
                loginResponse.loginResponseStatus = LoginStatus.WRONG_CREDENTIALS
            }
            return loginResponse
        }catch (e : Exception){
            Log.e("Login_User","Error while logging in : $e")
            loginResponse.loginResponseStatus = LoginStatus.EXCEPTION
            return loginResponse
        }
    }
}