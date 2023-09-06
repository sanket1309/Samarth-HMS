package com.samarthhms.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.samarthhms.R
import com.samarthhms.constants.Constants
import com.samarthhms.databinding.FragmentAddVisitBinding
import com.samarthhms.domain.Status
import com.samarthhms.models.PatientVisitDetails
import com.samarthhms.navigator.Navigator
import com.samarthhms.utils.DialogUtils
import com.samarthhms.utils.ToastUtils
import com.samarthhms.utils.UiDataDisplayUtils
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AddVisitFragment : Fragment() {

    private val addVisitViewModel: AddVisitViewModel by viewModels()

    @Inject
    lateinit var navigator : Navigator

    private lateinit var binding: FragmentAddVisitBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddVisitBinding.inflate(layoutInflater, container, false)
        val patientVisitDetails = AddVisitFragmentArgs.fromBundle(requireArguments()).patientVisitDetails
        UiDataDisplayUtils.displayVisitDetails(binding.root, patientVisitDetails)
        binding.addVisitButton.setOnClickListener{ onAddVisit(patientVisitDetails) }
        addVisitViewModel.addVisitStatus.observe(viewLifecycleOwner){ onAddVisitStatus(it) }
        return binding.root
    }

    private fun onAddVisit(patientVisitDetails: PatientVisitDetails){
        DialogUtils.popDialog(requireContext(),
        onPositive = { addVisitViewModel.addVisit(patientVisitDetails) },
        message = Constants.DialogMessages.ADD_VISIT,
        positiveMessage = Constants.DialogMessages.YES,
        negativeMessage = Constants.DialogMessages.NO)
    }

    private fun onAddVisitStatus(status: Status){
        when(status){
            Status.SUCCESS -> onSuccess()
            Status.FAILURE -> onFailure()
            else -> {}
        }
    }

    private fun onSuccess(){
        ToastUtils.showToast(requireContext(), Constants.Messages.ADDED_VISIT_SUCCESSFULLY)
        navigator.popBackStack(this, R.id.addPatientFragment, true)
    }

    private fun onFailure(){
        ToastUtils.showToast(requireContext(), Constants.Messages.SOMETHING_WENT_WRONG)
    }

}