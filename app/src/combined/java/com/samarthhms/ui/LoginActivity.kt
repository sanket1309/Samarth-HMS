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
import com.samarthhms.constants.Role
import com.samarthhms.databinding.ActivityLoginBinding
import com.samarthhms.domain.LoginStatus
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
        binding.loginButton
            .setOnClickListener {
            val username = binding.username.text.toString()
            val password = binding.password.text.toString()
            roleSelected = if (binding.adminRadioButton.isChecked) Role.ADMIN else Role.STAFF
            val credentials = Credentials("",roleSelected, username, password)
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
        Toast.makeText(this, "SUCCESSFULLY LOGGED IN", Toast.LENGTH_SHORT).show()
        navigator.showDashboard(this@LoginActivity, roleSelected, isLocked)
    }

    private fun onWrongCredentials(){
        changeColorOfInputFields(binding.usernameTitle, binding.username, R.color.red)
        changeColorOfInputFields(binding.passwordTitle, binding.password, R.color.red)
    }

    private fun changeColorOfInputFields(fieldTitle: TextView, fieldInput: EditText, color: Int){
        val colorValue = getColor(color)
        fieldTitle.setTextColor(colorValue)
        val fieldInputDrawable = fieldInput.background as StateListDrawable
        val dcs = fieldInputDrawable.constantState as DrawableContainerState
        val drawableItem = dcs.children[0] as GradientDrawable
        val pixels = R.dimen.login_edittext_background_stroke_width * resources.displayMetrics.density.toInt()
        drawableItem.setStroke(pixels, colorValue)
    }

    private fun onFailure(){
        Toast.makeText(this, "FAILED TO LOG IN", Toast.LENGTH_SHORT).show()
    }
}