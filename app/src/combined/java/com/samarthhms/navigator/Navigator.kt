package com.samarthhms.navigator

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.samarthhms.constants.LoggedState
import com.samarthhms.constants.Role
import com.samarthhms.domain.LoginStatusResponse
import com.samarthhms.ui.LoginActivity
import com.samarthhms.ui.MainActivity
import com.samarthhms.ui.StaffLockActivity
import com.samarthhms.ui.StaffMainActivity
import javax.inject.Inject

class Navigator @Inject constructor(){

    private fun showLogin(context: Context){
        context.startActivity(Intent(context,LoginActivity::class.java))
        finishActivity(context)
    }

    fun showDashboard(context: Context, role: Role, isLocked: Boolean){
        Log.i("NVIGATOR","SHOW DASHBOARD ROLE=${role.name}")
        when(role){
            Role.ADMIN -> context.startActivity(Intent(context,MainActivity::class.java))
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

    fun popBackStack(sourceFragment: Fragment, destinationId: Int, inclusive: Boolean){
        val navigationController = sourceFragment.findNavController()
        navigationController.popBackStack(destinationId, inclusive)
    }

    fun navigateToFragment(sourceFragment: Fragment, actionId: Int){
        val navigationController = sourceFragment.findNavController()
        navigationController.navigate(actionId)
    }

    fun navigateToFragment(sourceFragment: Fragment, action: NavDirections){
        val navigationController = sourceFragment.findNavController()
        navigationController.navigate(action)
    }

    private fun finishActivity(context: Context){
        if(context is Activity){
            context.finish()
        }
    }
}