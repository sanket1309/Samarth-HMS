package com.samarthhms.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.samarthhms.constants.Constants
import com.samarthhms.constants.Role
import com.samarthhms.databinding.FragmentAddAdminBinding
import com.samarthhms.domain.Status
import com.samarthhms.models.Admin
import com.samarthhms.models.AdminDetails
import com.samarthhms.navigator.Navigator
import com.samarthhms.utils.*
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AddAdminFragment : Fragment() {

    @Inject
    lateinit var navigator : Navigator

    private val viewModel: AddAdminViewModel by viewModels()

    private lateinit var binding: FragmentAddAdminBinding

    private lateinit var adminDetails: AdminDetails

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddAdminBinding.inflate(layoutInflater, container, false)
        binding.saveAdminButton.setOnClickListener { onSaveAdmin() }
        viewModel.addAdminStatus.observe(viewLifecycleOwner) { onAddAdmin(it) }
        binding.showPasswordButton.setOnClickListener { ShowPasswordUtils.onPasswordVisibilityToggle(binding.showPasswordButton, binding.password) }
        return binding.root
    }

    private fun onSaveAdmin(){
        try {
            ValidationUtils.validateAdminDetails(binding.root, requireContext(), resources)
            DialogUtils.popDialog(requireContext(),
                onPositive = {
                    adminDetails =UiDataExtractorUtils.getAdminDetails(binding.root)
                    viewModel.addAdmin(adminDetails)
                },
                message = Constants.DialogMessages.SAVE_ADMIN,
                positiveMessage = Constants.DialogMessages.YES,
                negativeMessage = Constants.DialogMessages.NO)
        }catch (e: Exception){
            Log.e("AddAdminFragment", "Exception while saving admin details")
        }
    }

    private fun onAddAdmin(status: Status){
        when (status) {
            Status.SUCCESS -> onSuccess()
            Status.FAILURE -> onFailure()
            else -> {}
        }
    }

    private fun onSuccess() {
        ToastUtils.showToast(requireContext(), Constants.Messages.ADDED_ADMIN_SUCCESSFULLY)
        navigator.navigateToFragment(this, AddAdminFragmentDirections.actionAddAdminFragmentToAdminSettingsFragment())
    }

    private fun onFailure() {
        ToastUtils.showToast(requireContext(), Constants.Messages.SOMETHING_WENT_WRONG)
    }
}

