package com.samarthhms.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.samarthhms.constants.Constants
import com.samarthhms.databinding.FragmentAddStaffBinding
import com.samarthhms.domain.Status
import com.samarthhms.navigator.Navigator
import com.samarthhms.utils.*
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AddStaffFragment : Fragment() {

    @Inject
    lateinit var navigator : Navigator

    private val viewModel: AddStaffViewModel by viewModels()

    private lateinit var binding: FragmentAddStaffBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddStaffBinding.inflate(layoutInflater, container, false)
        binding.saveStaffButton.setOnClickListener { onSaveStaff() }
        viewModel.addStaffStatus.observe(viewLifecycleOwner) { onAddStaffStatus(it) }
        binding.showPasswordButton.setOnClickListener { ShowPasswordUtils.onPasswordVisibilityToggle(binding.showPasswordButton, binding.password) }
        return binding.root
    }

    private fun onSaveStaff(){
        try {
            ValidationUtils.validateStaffDetails(binding.root, requireContext(), resources)
            DialogUtils.popDialog(requireContext(),
            onPositive = {
                val staffDetails =UiDataExtractorUtils.getStaffDetails(binding.root)
                viewModel.addStaff(staffDetails.staff!!, staffDetails.staffCredentials!!)
            },
            message = Constants.DialogMessages.SAVE_STAFF,
            positiveMessage = Constants.DialogMessages.YES,
            negativeMessage = Constants.DialogMessages.NO)
        }catch (e: Exception){
            Log.e("AddStaffFragment", "Exception while saving staff details")
        }
    }

    private fun onAddStaffStatus(status: Status){
        when (status) {
            Status.SUCCESS -> onSuccess()
            Status.FAILURE -> onFailure()
            else -> {}
        }
    }

    private fun onSuccess() {
        ToastUtils.showToast(requireContext(), Constants.Messages.ADDED_STAFF_SUCCESSFULLY)
        navigator.navigateToFragment(this, AddStaffFragmentDirections.actionAddStaffFragmentToStaffSettingsFragment())
    }

    private fun onFailure() {
        ToastUtils.showToast(requireContext(), Constants.Messages.SOMETHING_WENT_WRONG)
    }

}

