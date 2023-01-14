package com.samarthhms.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.samarthhms.constants.Role
import com.samarthhms.databinding.ActivityLoginBinding
import com.samarthhms.domain.LoginResponseStatus
import com.samarthhms.models.Credentials
import com.samarthhms.navigator.Navigator
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
        binding.button.setOnClickListener {
            val username = binding.editTextTextPersonName.text.toString()
            val password = binding.editTextTextPassword.text.toString()
            roleSelected = if (binding.radioButton1.isChecked) Role.ADMIN else Role.STAFF
            val credentials = Credentials("",roleSelected, username, password)
            loginViewModel.login(credentials)
            binding.button.isClickable = false
        }
        binding.msg.text = ""
        loginViewModel.loginUserStatus.observe(this) {
            binding.msg.text = it.toString()
            when(it){
                LoginResponseStatus.SUCCESS -> onSuccess()
                LoginResponseStatus.EXCEPTION -> onFailure()
                LoginResponseStatus.WRONG_CREDENTIALS -> onFailure()
                else -> {}
            }
            binding.button.isClickable = true
        }

    }

    private fun onSuccess(){
        Toast.makeText(this, "SUCCESSFULLY LOGGED IN", Toast.LENGTH_SHORT).show()
        navigator.showDashboard(this@LoginActivity, roleSelected)
    }

    private fun onFailure(){
        Toast.makeText(this, "FAILED TO LOG IN", Toast.LENGTH_SHORT).show()
    }
}