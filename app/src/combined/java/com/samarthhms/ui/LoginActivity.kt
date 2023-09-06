package com.samarthhms.ui

import android.graphics.drawable.DrawableContainer.DrawableContainerState
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.StateListDrawable
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.samarthhms.R
import com.samarthhms.constants.Constants
import com.samarthhms.constants.Role
import com.samarthhms.databinding.ActivityLoginBinding
import com.samarthhms.domain.LoginStatus
import com.samarthhms.models.Credentials
import com.samarthhms.navigator.Navigator
import com.samarthhms.utils.*
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    @Inject
    lateinit var navigator : Navigator

    private val loginViewModel: LoginViewModel by viewModels()

    private lateinit var binding: ActivityLoginBinding

    private var roleSelected : Role = Role.NONE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initializeView()
    }

    private fun initializeView(){
        binding.loginButton.setOnClickListener {
            try{
//                ValidateIndividualFormUtils.validateIndividualForm(binding.root, this, resources)
            }catch (e: Exception){
                ToastUtils.showToast(this, Constants.Messages.VALIDATION_FAILED)
                return@setOnClickListener
            }
            val credentials = UiDataExtractorUtils.getCredentials(binding.root)
            credentials.role = if (binding.adminRadioButton.isChecked) Role.ADMIN else Role.STAFF
            roleSelected = credentials.role
            loginViewModel.login(credentials)
            binding.loginButton.isClickable = false
        }
        loginViewModel.loginUserResponse.observe(this) {
            when(it.loginResponseStatus){
                LoginStatus.SUCCESS -> onSuccess(it.isLocked)
                LoginStatus.EXCEPTION -> onFailure()
                LoginStatus.WRONG_CREDENTIALS -> onWrongCredentials()
                else -> {}
            }
            binding.loginButton.isClickable = true
        }
    }

    private fun onSuccess(isLocked: Boolean){
        ToastUtils.showToast(this, Constants.Messages.SUCCESSFULLY_LOGGED_IN)
        navigator.showDashboard(this@LoginActivity, roleSelected, isLocked)
    }

    private fun onWrongCredentials(){
        InputFieldColorUtils.changeColorOfInputFields(binding.usernameTitle, binding.username, R.color.red, this, resources)
        InputFieldColorUtils.changeColorOfInputFields(binding.passwordTitle, binding.password, R.color.red, this, resources)
    }

    private fun onFailure(){
        ToastUtils.showToast(this, Constants.Messages.FAILED_TO_LOG_IN)
    }
}