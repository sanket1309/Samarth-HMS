package com.samarthhms.navigator

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.samarthhms.constants.LoggedState
import com.samarthhms.constants.Role
import com.samarthhms.domain.LoginStatusResponse
import com.samarthhms.ui.LoginActivity
import com.samarthhms.ui.MainActivity
import javax.inject.Inject

class Navigator @Inject constructor(){

    private fun showLogin(context: Context){
        context.startActivity(Intent(context,LoginActivity::class.java))
        finishActivity(context)
    }

    private fun showDashboard(context: Context, role: Role){
        when(role){
            Role.ADMIN -> context.startActivity(Intent(context,MainActivity::class.java))
            Role.STAFF -> context.startActivity(Intent(context,LoginActivity::class.java))
            else -> {
                showLogin(context)
                return
            }
        }
        finishActivity(context)
    }

    fun showMain(context: Context, loginStatusResponse: LoginStatusResponse){
        when(loginStatusResponse.loggedState){
            LoggedState.LOGGED_OUT -> showLogin(context)
            LoggedState.LOGGED_IN -> showDashboard(context, loginStatusResponse.role)
        }
    }

    private fun finishActivity(context: Context){
        if(context is Activity){
            context.finish()
        }
    }
}