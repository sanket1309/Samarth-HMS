package com.samarthhms.navigator

import android.content.Context
import android.content.Intent
import com.samarthhms.ui.LoginActivity
import javax.inject.Inject

class Navigator @Inject constructor(){
    fun showLogin(context: Context){
        context.startActivity(Intent(context,LoginActivity::class.java))
    }
}