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
import com.samarthhms.constants.Constants
import com.samarthhms.constants.Gender
import com.samarthhms.constants.Role
import com.samarthhms.databinding.FragmentAdminDetailsBinding
import com.samarthhms.domain.Status
import com.samarthhms.models.Admin
import com.samarthhms.models.AdminDetails
import com.samarthhms.models.Credentials
import com.samarthhms.models.IndividualFormData
import com.samarthhms.utils.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AdminDetailsFragment : Fragment() {

    private val viewModel: AdminDetailsViewModel by viewModels()

    private lateinit var binding: FragmentAdminDetailsBinding

    private lateinit var adminDetails: AdminDetails

    private lateinit var admin: Admin

    private var isEditable: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAdminDetailsBinding.inflate(layoutInflater, container, false)
        adminDetails = AdminDetailsFragmentArgs.fromBundle(requireArguments()).adminDetails
        admin = adminDetails.admin
        updateEditability(false)
        UiDataDisplayUtils.displayIndividualFormWithAddress(binding.root, admin)
        UiDataDisplayUtils.displayCredentialDetails(binding.root, adminDetails.adminCredentials)

        binding.saveAdminButton.visibility = View.GONE
        binding.saveAdminButton.setOnClickListener { onSaveAdmin() }
        binding.editAdminButton.setOnClickListener { onEditAdmin() }
        binding.deleteAdminButton.setOnClickListener { onDeleteAdmin() }
        binding.showPasswordButton.setOnClickListener { onShowPassword() }
        viewModel.addAdminStatus.observe(viewLifecycleOwner) { onAddAdmin(it) }
        viewModel.removeAdminStatus.observe(viewLifecycleOwner) { onRemoveAdmin(it) }

        return binding.root
    }

    private fun onDeleteAdmin(){
        DialogUtils.popDialog(requireContext(),
            null,
            null,
            Constants.DialogMessages.DELETE_ADMIN,
            Constants.DialogMessages.YES,
            Constants.DialogMessages.NO)
    }

    private fun onShowPassword(){
        ShowPasswordUtils.onPasswordVisibilityToggle(binding.showPasswordButton, binding.password,false)
    }

    private fun onEditAdmin(){
        EditableUtils.updateEditabilityForAdminDetails(isEditable = true, binding.root)
    }

    private fun onRemoveAdmin(status: Status){
        when (status) {
            Status.SUCCESS -> {
                onSuccess(Constants.Messages.ADMIN_DELETED)
                val action = AdminDetailsFragmentDirections.actionAdminDetailsFragmentToAdminSettingsFragment()
                findNavController().navigate(action)
            }
            Status.FAILURE -> onFailure()
            else -> {}
        }
    }

    private fun onAddAdmin(status: Status){
        when (status) {
            Status.SUCCESS -> onSuccess(Constants.Messages.ADMIN_DETAILS_UPDATED)
            Status.FAILURE -> onFailure()
            else -> {}
        }
    }

    private fun onSaveAdmin(){
        ValidateIndividualFormUtils.validateIndividualFormWithAddress(binding.root, requireContext(), resources)
        val individualFormData =UiDataExtractorUtils.extractData(binding.root, Admin::class.java)
        val newAdmin = individualFormData.data!!
        val credentials = individualFormData.credentials!!
        credentials.id = admin.adminId
        credentials.role = Role.ADMIN
        val newAdminDetails = AdminDetails(admin.adminId, newAdmin, null, credentials)

        if(areEqual(adminDetails, newAdminDetails)){
            ToastUtils.showToast(requireContext(), Constants.Messages.NOTHING_UPDATED)
            return
        }
        DialogUtils.popDialog(requireContext(), {
            viewModel.addAdmin(newAdminDetails)
            adminDetails = newAdminDetails
        }, null,
            Constants.DialogMessages.UPDATE_ADMIN,Constants.DialogMessages.YES,Constants.DialogMessages.NO)
    }


    fun areEqual(a: AdminDetails, b: AdminDetails): Boolean{
        return a.admin.firstName == b.admin.firstName &&
                a.admin.middleName == b.admin.middleName &&
                a.admin.lastName == b.admin.lastName &&
                a.admin.gender == b.admin.gender &&
                a.admin.dateOfBirth == b.admin.dateOfBirth &&
                a.admin.contactNumber == b.admin.contactNumber &&
                a.admin.address == b.admin.address &&
                a.adminCredentials.username == b.adminCredentials.username &&
                a.adminCredentials.password == b.adminCredentials.password
    }

    private fun updateEditability(isEditable: Boolean){
        EditableUtils.updateEditabilityForAdminDetails(isEditable = isEditable, binding.root)
        this.isEditable = isEditable
    }


    private fun onSuccess(message: String) {
        updateEditability(false)
        ToastUtils.showToast(requireContext(), message)
    }

    private fun onFailure() {
        ToastUtils.showToast(requireContext(), Constants.Messages.SOMETHING_WENT_WRONG)
    }
}

