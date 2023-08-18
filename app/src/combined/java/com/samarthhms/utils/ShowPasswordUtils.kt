package com.samarthhms.utils

import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.samarthhms.constants.Constants

class ShowPasswordUtils {
    companion object{
        fun onPasswordVisibilityToggle(visibilityButton: TextView, passwordInput: EditText, toHide: Boolean){
            if(toHide) onHide(visibilityButton, passwordInput)
            else onShow(visibilityButton, passwordInput)
        }

        fun onPasswordVisibilityToggle(visibilityButton: TextView, passwordInput: EditText){
            val state = visibilityButton.text.toString()
            if(state == Constants.SHOW) onShow(visibilityButton, passwordInput)
            else onHide(visibilityButton, passwordInput)
        }

        private fun onShow(visibilityButton: TextView, passwordInput: EditText){
            passwordInput.transformationMethod = HideReturnsTransformationMethod.getInstance()
            visibilityButton.text = Constants.HIDE
        }

        private fun onHide(visibilityButton: TextView, passwordInput: EditText){
            passwordInput.transformationMethod = PasswordTransformationMethod.getInstance()
            visibilityButton.text = Constants.SHOW
        }

    }
}