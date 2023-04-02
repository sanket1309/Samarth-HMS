package com.samarthhms.ui

import android.content.Context
import android.graphics.drawable.DrawableContainer
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.StateListDrawable
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.samarthhms.R
import com.samarthhms.constants.Gender
import com.samarthhms.constants.Role
import com.samarthhms.databinding.FragmentAddAdminBinding
import com.samarthhms.databinding.FragmentAddStaffBinding
import com.samarthhms.domain.Status
import com.samarthhms.models.Admin
import com.samarthhms.models.AdminDetails
import com.samarthhms.models.Credentials
import com.samarthhms.models.Staff
import com.samarthhms.utils.DateTimeUtils
import com.samarthhms.utils.StringUtils
import com.samarthhms.utils.Validation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddAdminFragment : Fragment() {

    private val viewModel: AddAdminViewModel by viewModels()

    private lateinit var binding: FragmentAddAdminBinding

    private lateinit var adminDetails: AdminDetails

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddAdminBinding.inflate(layoutInflater, container, false)
        binding.saveAdminButton.setOnClickListener {
            var invalidColor = R.color.red
            var validColor = R.color.blue_theme

            val firstName = binding.firstName.text.toString()
            if (!Validation.validateName(firstName)) {
                changeColorOfInputFields(binding.firstNameTitle, binding.firstName, invalidColor)
                return@setOnClickListener
            } else {
                changeColorOfInputFields(binding.firstNameTitle, binding.firstName, validColor)
            }

            val middleName = binding.middleName.text.toString()
            if (!Validation.validateName(middleName)) {
                changeColorOfInputFields(binding.middleNameTitle, binding.middleName, invalidColor)
                return@setOnClickListener
            } else {
                changeColorOfInputFields(binding.middleNameTitle, binding.middleName, validColor)
            }

            val lastName = binding.lastName.text.toString()
            if (!Validation.validateName(lastName)) {
                changeColorOfInputFields(binding.lastNameTitle, binding.lastName, invalidColor)
                return@setOnClickListener
            } else {
                changeColorOfInputFields(binding.lastNameTitle, binding.lastName, validColor)
            }

            val gender =
                if (binding.genderMaleRadioGroupButton.isChecked) Gender.MALE else Gender.FEMALE

            val contactNumber = binding.contactNumber.text.toString().replace(" ", "")
            if (!Validation.validateContactNumber(contactNumber)) {
                changeColorOfInputFields(
                    binding.contactNumberTitle,
                    binding.contactNumber,
                    invalidColor
                )
                return@setOnClickListener
            } else {
                changeColorOfInputFields(
                    binding.contactNumberTitle,
                    binding.contactNumber,
                    validColor
                )
            }

            val dob = binding.dateOfBirth.text.toString()
            if (!Validation.validateDate(dob)) {
                changeColorOfInputFields(binding.dateOfBirthTitle, binding.dateOfBirth, invalidColor)
                return@setOnClickListener
            } else {
                changeColorOfInputFields(binding.dateOfBirthTitle, binding.dateOfBirth, validColor)
            }

            val address = binding.address.text.toString()
            if (address.isBlank()) {
                changeColorOfInputFields(binding.addressTitle, binding.address, invalidColor)
                return@setOnClickListener
            } else {
                changeColorOfInputFields(binding.addressTitle, binding.address, validColor)
            }

            val username = binding.username.text.toString()
            if (!Validation.validateUserName(username)) {
                changeColorOfInputFields(binding.setUsernameTitle, binding.username, invalidColor)
                return@setOnClickListener
            } else {
                changeColorOfInputFields(binding.setUsernameTitle, binding.username, validColor)
            }

            val password = binding.password.text.toString()
            if (!Validation.validatePassword(password)) {
                changeColorOfInputFields(binding.setPasswordTitle, binding.password, invalidColor)
                return@setOnClickListener
            } else {
                changeColorOfInputFields(binding.setPasswordTitle, binding.password, validColor)
            }

            adminDetails = AdminDetails("",Admin(
                "",
                StringUtils.formatName(firstName),
                StringUtils.formatName(middleName),
                StringUtils.formatName(lastName),
                gender,
                contactNumber,
                DateTimeUtils.getLocalDateTimeFromDate(dob),
                address
            ),null,Credentials("", Role.ADMIN, username, password))
            viewModel.addAdmin(adminDetails)
        }

        viewModel.addAdminStatus.observe(viewLifecycleOwner) {
            when (it) {
                Status.SUCCESS -> onSuccess()
                Status.FAILURE -> onFailure()
                else -> {}
            }
        }

        binding.showPasswordButton.setOnClickListener {
            onShowPassword()
        }

        return binding.root
    }

    fun onShowPassword(){
        var state = binding.showPasswordButton.text.toString()
        if(state == "Show"){
            binding.password.transformationMethod = HideReturnsTransformationMethod.getInstance()
            state = "Hide"
        }
        else{
            binding.password.transformationMethod = PasswordTransformationMethod.getInstance()
            state = "Show"
        }
        binding.showPasswordButton.text = state
    }

    private fun onSuccess() {
        Toast.makeText(activity, "Added Admin Successfully", Toast.LENGTH_SHORT).show()
    }

    private fun onFailure() {
        Toast.makeText(activity, "Something Went Wrong", Toast.LENGTH_SHORT).show()
    }

    private fun changeColorOfInputFields(fieldTitle: TextView, fieldInput: EditText, color: Int) {
        val colorValue = ContextCompat.getColor(activity as Context, color)
        fieldTitle.setTextColor(colorValue)
        val fieldInputDrawable = fieldInput.background as StateListDrawable
        val dcs = fieldInputDrawable.constantState as DrawableContainer.DrawableContainerState
        val drawableItem = dcs.children[0] as GradientDrawable
        val pixels =
            R.dimen.login_edittext_background_stroke_width * resources.displayMetrics.density.toInt()
        drawableItem.setStroke(pixels, colorValue)
    }
}

