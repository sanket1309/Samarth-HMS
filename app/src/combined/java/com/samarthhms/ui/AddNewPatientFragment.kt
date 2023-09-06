package com.samarthhms.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.samarthhms.constants.Constants
import com.samarthhms.databinding.FragmentAddNewPatientBinding
import com.samarthhms.domain.Status
import com.samarthhms.models.PatientVisitDetails
import com.samarthhms.navigator.Navigator
import com.samarthhms.utils.DialogUtils
import com.samarthhms.utils.ToastUtils
import com.samarthhms.utils.UiDataExtractorUtils
import com.samarthhms.utils.ValidationUtils
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AddNewPatientFragment : Fragment() {

    @Inject
    lateinit var navigator : Navigator

    private lateinit var binding: FragmentAddNewPatientBinding

    private val addNewPatientViewModel: AddNewPatientViewModel by viewModels()

    private lateinit var patientVisitDetails: PatientVisitDetails

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddNewPatientBinding.inflate(layoutInflater, container, false)
        binding.savePatientButton.setOnClickListener{ onSavePatient() }
        addNewPatientViewModel.addPatientStatus.observe(viewLifecycleOwner){ onAddPatientStatus(it) }
        return binding.root
    }

    private fun onSavePatient(){
        try {
            ValidationUtils.validatePatient(binding.root, requireContext(), resources)
            DialogUtils.popDialog(requireContext(),
                onPositive = {
                patientVisitDetails = PatientVisitDetails()
                patientVisitDetails.patient = UiDataExtractorUtils.getPatient(binding.root)
                patientVisitDetails.ageInText = patientVisitDetails.patient?.ageInText
                addNewPatientViewModel.addPatient(patientVisitDetails.patient!!)
            },
            message = Constants.DialogMessages.SAVE_PATIENT,
            positiveMessage = Constants.DialogMessages.YES,
            negativeMessage = Constants.DialogMessages.NO)
        }catch (e: Exception){
            Log.e("AddNewPatientFragment","Exception while adding new patient")
        }
    }

    private fun onAddPatientStatus(status: Status){
        when(status){
            Status.SUCCESS -> onSuccess()
            Status.FAILURE -> onFailure()
            else -> {}
        }
    }

    private fun onSuccess(){
        ToastUtils.showToast(requireContext(), Constants.Messages.ADDED_RECORD_SUCCESSFULLY)
        navigator.navigateToFragment(this, AddNewPatientFragmentDirections.actionAddNewPatientFragmentToAddVisitFragment(patientVisitDetails))
    }

    private fun onFailure(){
        ToastUtils.showToast(requireContext(), Constants.Messages.SOMETHING_WENT_WRONG)
    }

}