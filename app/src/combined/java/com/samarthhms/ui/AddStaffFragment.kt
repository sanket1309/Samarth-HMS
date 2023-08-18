package com.samarthhms.ui

import android.content.Context
import android.graphics.drawable.DrawableContainer
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.StateListDrawable
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.samarthhms.R
import com.samarthhms.constants.Constants
import com.samarthhms.constants.Gender
import com.samarthhms.constants.Role
import com.samarthhms.databinding.FragmentAddStaffBinding
import com.samarthhms.domain.Status
import com.samarthhms.models.Admin
import com.samarthhms.models.AdminDetails
import com.samarthhms.models.Credentials
import com.samarthhms.models.Staff
import com.samarthhms.utils.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddStaffFragment : Fragment() {

    private val viewModel: AddStaffViewModel by viewModels()

    private lateinit var binding: FragmentAddStaffBinding

    private lateinit var staff: Staff

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddStaffBinding.inflate(layoutInflater, container, false)
        binding.saveStaffButton.setOnClickListener {
            try {
                ValidateIndividualFormUtils.validateIndividualFormWithAddress(binding.root, requireContext(), resources)
                val individualFormData =UiDataExtractorUtils.extractData(binding.root, Staff::class.java)
                individualFormData.credentials!!.role = Role.STAFF
                staff = individualFormData.data!!
                viewModel.addStaff(staff, individualFormData.credentials!!)
            }catch (e: Exception){
                Log.e("AddStaffFragment", "Exception while saving staff details")
            }
        }

        viewModel.addStaffStatus.observe(viewLifecycleOwner) {
            when (it) {
                Status.SUCCESS -> onSuccess()
                Status.FAILURE -> onFailure()
                else -> {}
            }
        }

        binding.showPasswordButton.setOnClickListener {
            ShowPasswordUtils.onPasswordVisibilityToggle(binding.showPasswordButton, binding.password)
        }
        return binding.root
    }

    private fun onSuccess() {
        ToastUtils.showToast(requireContext(), "Added Staff Successfully")
    }

    private fun onFailure() {
        ToastUtils.showToast(requireContext(), "Something Went Wrong")
    }

}

