package com.example.samarthhms.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.example.samarthhms.constants.Role
import com.example.samarthhms.databinding.ActivityLoginBinding
import com.example.samarthhms.domain.LoginResponseStatus
import com.example.samarthhms.models.Credentials
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private val loginViewModel: LoginViewModel by viewModels()

    private lateinit var binding: ActivityLoginBinding

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
            val role = if (binding.radioButton1.isChecked) Role.ADMIN else Role.STAFF
            val credentials = Credentials("",role, username, password)
            loginViewModel.login(credentials)
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
        }

    }

    private fun onSuccess(){
        Toast.makeText(this, "SUCCESSFULLY LOGGED IN", Toast.LENGTH_SHORT).show()
    }

    private fun onFailure(){
        Toast.makeText(this, "FAILED TO LOG IN", Toast.LENGTH_SHORT).show()
    }
}