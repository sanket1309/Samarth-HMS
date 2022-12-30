package com.example.samarthhms.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.samarthhms.R
import com.example.samarthhms.constants.Role
import com.example.samarthhms.databinding.ActivityLoginBinding
import com.example.samarthhms.domain.LoginResponseStatus
import com.example.samarthhms.models.Credentials

class LoginActivity : AppCompatActivity() {

    val loginViewModel: LoginViewModel by viewModels<LoginViewModel>()

    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
    }

    fun initializeView(){
        binding.button.setOnClickListener {
            val username = binding.editTextTextPersonName.text.toString()
            val password = binding.editTextTextPassword.text.toString()
            val role = if (binding.radioButton1.isChecked) Role.ADMIN else Role.STAFF
            val credentials = Credentials("",role, username, password)
            loginViewModel.login(credentials)
        }
        loginViewModel.loginUserStatus.observe(this, Observer {
            when(it){
                LoginResponseStatus.SUCCESS -> onSuccess()
                else -> onFailure()
            }
        })

    }

    private fun onSuccess(){
        Toast.makeText(this, "SUCCESSFULLY LOGGED IN", Toast.LENGTH_SHORT).show()
    }

    private fun onFailure(){
        Toast.makeText(this, "FAILED TO LOG IN", Toast.LENGTH_SHORT).show()
    }
}