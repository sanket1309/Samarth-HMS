package com.samarthhms.ui

import android.content.Context
import android.content.DialogInterface
import android.graphics.drawable.DrawableContainer
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.StateListDrawable
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.samarthhms.R
import com.samarthhms.constants.Gender
import com.samarthhms.constants.Role
import com.samarthhms.databinding.FragmentStaffDetailsBinding
import com.samarthhms.domain.Status
import com.samarthhms.models.Credentials
import com.samarthhms.models.Staff
import com.samarthhms.models.StaffDetails
import com.samarthhms.models.StaffStatus
import com.samarthhms.utils.StringUtils
import com.samarthhms.utils.Validation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StaffDetailsFragment : Fragment() {

    private val viewModel: StaffDetailsViewModel by viewModels()

    private lateinit var binding: FragmentStaffDetailsBinding

    private lateinit var staffDetails: StaffDetails

    private lateinit var staff: Staff

    private var isEdit: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStaffDetailsBinding.inflate(layoutInflater, container, false)
        staffDetails = StaffDetailsFragmentArgs.fromBundle(requireArguments()).staffDetails
        staff = staffDetails.staff!!
        onSave()

        binding.firstName.setText(staff.firstName)
        binding.middleName.setText(staff.middleName)
        binding.lastName.setText(staff.lastName)
        if(staff.gender == Gender.MALE){
            binding.genderMaleRadioGroupButton.isChecked = true
        }
        else{
            binding.genderFemaleRadioGroupButton.isChecked = true
        }
        binding.contactNumber.setText(staff.contactNumber)
        binding.address.setText(staff.address)
        binding.username.setText(staffDetails.staffCredentials!!.username)
        binding.password.setText(staffDetails.staffCredentials!!.password)
        changeEditField(false)

        binding.saveStaffButton.visibility = View.GONE
        binding.saveStaffButton.setOnClickListener {
            val invalidColor = R.color.red
            val validColor = R.color.blue_theme

            val firstName = binding.firstName.text.toString()
            if (!Validation.validateName(firstName)) {
                Toast.makeText(activity, "Invalid First Name", Toast.LENGTH_SHORT).show()
                changeColorOfInputFields(binding.firstNameTitle, binding.firstName, invalidColor)
                return@setOnClickListener
            } else {
                changeColorOfInputFields(binding.firstNameTitle, binding.firstName, validColor)
            }

            val middleName = binding.middleName.text.toString()
            if (!Validation.validateName(middleName)) {
                Toast.makeText(activity, "Invalid Middle Name", Toast.LENGTH_SHORT).show()
                changeColorOfInputFields(binding.middleNameTitle, binding.middleName, invalidColor)
                return@setOnClickListener
            } else {
                changeColorOfInputFields(binding.middleNameTitle, binding.middleName, validColor)
            }

            val lastName = binding.lastName.text.toString()
            if (!Validation.validateName(lastName)) {
                Toast.makeText(activity, "Invalid Last Name", Toast.LENGTH_SHORT).show()
                changeColorOfInputFields(binding.lastNameTitle, binding.lastName, invalidColor)
                return@setOnClickListener
            } else {
                changeColorOfInputFields(binding.lastNameTitle, binding.lastName, validColor)
            }

            val gender =
                if (binding.genderMaleRadioGroupButton.isChecked) Gender.MALE else Gender.FEMALE

            val contactNumber = binding.contactNumber.text.toString().replace(" ", "")
            if (!Validation.validateContactNumber(contactNumber)) {
                Toast.makeText(activity, "Invalid Contact Number", Toast.LENGTH_SHORT).show()
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

            val address = binding.address.text.toString()
            if (address.isBlank()) {
                Toast.makeText(activity, "Invalid Address", Toast.LENGTH_SHORT).show()
                changeColorOfInputFields(binding.addressTitle, binding.address, invalidColor)
                return@setOnClickListener
            } else {
                changeColorOfInputFields(binding.addressTitle, binding.address, validColor)
            }

            val username = binding.username.text.toString()
            if (!Validation.validateUserName(username)) {
                Toast.makeText(activity, "Invalid Username", Toast.LENGTH_SHORT).show()
                changeColorOfInputFields(binding.setUsernameTitle, binding.username, invalidColor)
                return@setOnClickListener
            } else {
                changeColorOfInputFields(binding.setUsernameTitle, binding.username, validColor)
            }

            val password = binding.password.text.toString()
            if (!Validation.validatePassword(password)) {
                Toast.makeText(activity, "Invalid Password", Toast.LENGTH_SHORT).show()
                changeColorOfInputFields(binding.setPasswordTitle, binding.password, invalidColor)
                return@setOnClickListener
            } else {
                changeColorOfInputFields(binding.setPasswordTitle, binding.password, validColor)
            }

            val newStaffDetails = StaffDetails(staff.staffId,
                Staff(
                staff.adminId,
                staff.staffId,
                StringUtils.formatName(firstName),
                StringUtils.formatName(middleName),
                StringUtils.formatName(lastName),
                gender,
                contactNumber,
                address
            ),
            StaffStatus(staff.staffId),
            Credentials(staff.staffId, Role.STAFF, username, password))
            if(areEqual(staffDetails, newStaffDetails)){
                Toast.makeText(activity, "Nothing has been updated", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val dialogClickListener = DialogInterface.OnClickListener{
                    dialog, which ->
                when(which){
                    DialogInterface.BUTTON_POSITIVE -> {
                        viewModel.addStaff(newStaffDetails)
                        staffDetails = newStaffDetails
                    }
                    DialogInterface.BUTTON_NEGATIVE -> {
                        dialog.dismiss()
                    }
                }
            }
            val alertDialogBuilder = AlertDialog.Builder(requireContext())
            alertDialogBuilder.setMessage("Are you sure, you want to update staff?")
                .setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener)
                .show()
        }

        binding.editStaffButton.setOnClickListener {
            onEdit()
        }

        binding.deleteStaffButton.setOnClickListener {
            val dialogClickListener = DialogInterface.OnClickListener{
                    dialog, which ->
                when(which){
                    DialogInterface.BUTTON_POSITIVE -> {
                        viewModel.removeStaff(staff.staffId)
                    }
                    DialogInterface.BUTTON_NEGATIVE -> {
                        dialog.dismiss()
                    }
                }
            }
            val alertDialogBuilder = AlertDialog.Builder(requireContext())
            alertDialogBuilder.setMessage("Are you sure, you want to delete?")
                .setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener)
                .show()
        }

        binding.showPasswordButton.setOnClickListener {
            onShowPassword()
        }

        viewModel.addStaffStatus.observe(viewLifecycleOwner) {
            when (it) {
                Status.SUCCESS -> onSuccess("Updated Staff Details")
                Status.FAILURE -> onFailure()
                else -> {}
            }
        }

        viewModel.removeStaffStatus.observe(viewLifecycleOwner) {
            when (it) {
                Status.SUCCESS -> {
                    onSuccess("Removed Staff")
                    val action = StaffDetailsFragmentDirections.actionStaffDetailsFragmentToStaffSettingsFragment()
                    findNavController().navigate(action)
                }
                Status.FAILURE -> onFailure()
                else -> {}
            }
        }

        return binding.root
    }

    private fun areEqual(a: StaffDetails, b: StaffDetails): Boolean{
        return a.staff!!.firstName == b.staff!!.firstName &&
               a.staff.middleName == b.staff.middleName &&
               a.staff.lastName == b.staff.lastName &&
               a.staff.gender == b.staff.gender &&
               a.staff.contactNumber == b.staff.contactNumber &&
               a.staff.address == b.staff.address &&
               a.staffCredentials!!.username == b.staffCredentials!!.username &&
               a.staffCredentials!!.password == b.staffCredentials!!.password
    }

    fun onEdit(){
        binding.editStaffButton.visibility = View.INVISIBLE
        binding.saveStaffButton.visibility = View.VISIBLE
        isEdit = true
        changeEditField(true)
        onHidePassword()
    }

    private fun onSave(){
        binding.saveStaffButton.visibility = View.INVISIBLE
        binding.editStaffButton.visibility = View.VISIBLE
        isEdit = false
        changeEditField(false)
        onHidePassword()
    }

    private fun changeEditField(isEdit: Boolean){
        val drawable = if(!isEdit) R.drawable.add_visit_edittext_background else R.drawable.login_edittext_background
        binding.firstName.setBackgroundResource(drawable)
        onEditable(isEdit, binding.firstName)
        binding.middleName.setBackgroundResource(drawable)
        onEditable(isEdit, binding.middleName)
        binding.lastName.setBackgroundResource(drawable)
        onEditable(isEdit, binding.lastName)
        binding.contactNumber.setBackgroundResource(drawable)
        onEditable(isEdit, binding.contactNumber)
        binding.address.setBackgroundResource(drawable)
        onEditable(isEdit, binding.address)
        binding.username.setBackgroundResource(drawable)
        onEditable(isEdit, binding.username)
        binding.password.setBackgroundResource(drawable)
        onEditable(isEdit, binding.password)
        binding.genderMaleRadioGroupButton.isEnabled = isEdit
        binding.genderFemaleRadioGroupButton.isEnabled = isEdit
        binding.showPasswordButton.visibility = if(!isEdit) View.GONE else View.VISIBLE
    }

    private fun onEditable(isEdit: Boolean, editText: EditText){
        editText.isClickable=isEdit
        editText.isCursorVisible=isEdit
        editText.isFocusable=isEdit
        editText.isFocusableInTouchMode=isEdit
    }

    private fun onShowPassword(){
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

    private fun onHidePassword(){
        binding.password.transformationMethod = PasswordTransformationMethod.getInstance()
        val state = "Show"
        binding.showPasswordButton.text = state
    }

    private fun onSuccess(message: String) {
        onSave()
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
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

