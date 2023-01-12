package com.samarthhms.navigator

import android.content.Context
import android.content.Intent
import com.samarthhms.constants.LoggedState
import com.samarthhms.constants.Role
import com.samarthhms.domain.LoginStatusResponse
import com.samarthhms.ui.LoginActivity
import javax.inject.Inject

class Navigator @Inject constructor(){

    private fun showLogin(context: Context){
        context.startActivity(Intent(context,LoginActivity::class.java))
    }

    fun showDashboard(context: Context, role: Role){
        when(role){
            Role.ADMIN -> context.startActivity(Intent(context,LoginActivity::class.java))
            Role.STAFF -> context.startActivity(Intent(context,LoginActivity::class.java))
            else -> {}
        }
    }

    fun showMain(context: Context, loginStatusResponse: LoginStatusResponse){
        when(loginStatusResponse.loggedState){
            LoggedState.LOGGED_OUT -> showLogin(context)
            LoggedState.LOGGED_IN -> showDashboard(context, loginStatusResponse.role)
        }
    }
}