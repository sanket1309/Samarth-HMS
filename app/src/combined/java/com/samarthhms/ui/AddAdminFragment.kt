package com.samarthhms.ui

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
import com.samarthhms.databinding.FragmentAddAdminBinding
import com.samarthhms.domain.Status
import com.samarthhms.models.Admin
import com.samarthhms.models.AdminDetails
import com.samarthhms.models.Credentials
import com.samarthhms.utils.*
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDateTime

@AndroidEntryPoint
class AddAdminFragment : Fragment() {

    private val viewModel: AddAdminViewModel by viewModels()

    private lateinit var binding: FragmentAddAdminBinding

    private lateinit var adminDetails: AdminDetails

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddAdminBinding.inflate(layoutInflater, container, false)
        binding.saveAdminButton.setOnClickListener {
            try {
                ValidateIndividualFormUtils.validateIndividualFormWithAddress(binding.root, requireContext(), resources)
                val individualFormData =UiDataExtractorUtils.extractData(binding.root, Admin::class.java)
                individualFormData.credentials!!.role = Role.ADMIN
                adminDetails = AdminDetails(admin = individualFormData.data!!, adminCredentials = individualFormData.credentials!!)
                viewModel.addAdmin(adminDetails)
            }catch (e: Exception){
                Log.e("AddAdminFragment", "Exception while saving admin details")
            }
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
}

