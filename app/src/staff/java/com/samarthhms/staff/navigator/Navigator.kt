package com.samarthhms.staff.navigator

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.samarthhms.staff.constants.LoggedState
import com.samarthhms.staff.constants.Role
import com.samarthhms.staff.domain.LoginStatusResponse
import com.samarthhms.staff.ui.LoginActivity
import com.samarthhms.staff.ui.StaffLockActivity
import com.samarthhms.staff.ui.StaffMainActivity
import javax.inject.Inject

class Navigator @Inject constructor(){

    private fun showLogin(context: Context){
        context.startActivity(Intent(context,LoginActivity::class.java))
        finishActivity(context)
    }

    fun showDashboard(context: Context, role: Role, isLocked: Boolean){
        when(role){
//            Role.ADMIN -> context.startActivity(Intent(context,StaffMainActivity::class.java))
            Role.STAFF -> {
                if(!isLocked){
                    context.startActivity(Intent(context,StaffMainActivity::class.java))
                }
                else{
                    context.startActivity(Intent(context,StaffLockActivity::class.java))
                }
            }
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
            LoggedState.LOGGED_IN -> showDashboard(context, loginStatusResponse.role, loginStatusResponse.isLocked)
        }
    }

    private fun finishActivity(context: Context){
        if(context is Activity){
            context.finish()
        }
    }
}