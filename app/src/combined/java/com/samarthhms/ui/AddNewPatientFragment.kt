package com.samarthhms.ui

import android.content.Context
import android.graphics.drawable.DrawableContainer
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.StateListDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.getColor
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.samarthhms.R
import com.samarthhms.constants.Gender
import com.samarthhms.databinding.FragmentAddNewPatientBinding
import com.samarthhms.domain.Status
import com.samarthhms.models.Admin
import com.samarthhms.models.Patient
import com.samarthhms.navigator.Navigator
import com.samarthhms.utils.*
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AddNewPatientFragment : Fragment() {

    @Inject
    lateinit var navigator : Navigator

    private lateinit var binding: FragmentAddNewPatientBinding

    private val addNewPatientViewModel: AddNewPatientViewModel by viewModels()

    private lateinit var patientData: Patient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddNewPatientBinding.inflate(layoutInflater, container, false)
        binding.savePatientButton.setOnClickListener{
            ValidateIndividualFormUtils.validateIndividualFormWithLocation(binding.root, requireContext(), resources)
            val individualFormData =UiDataExtractorUtils.extractDataWithLocation(binding.root, Patient::class.java)
            val patient = individualFormData.data!!
            patientData = patient
            addNewPatientViewModel.addPatient(patient)
        }

        addNewPatientViewModel.addPatientStatus.observe(viewLifecycleOwner){
            when(it){
                Status.SUCCESS -> onSuccess()
                Status.FAILURE -> onFailure()
                else -> {}
            }
        }

        return binding.root
    }

    private fun onSuccess(){
        Toast.makeText(activity, "Added Record Successfully", Toast.LENGTH_SHORT).show()
        val controller = findNavController()
        val action = AddNewPatientFragmentDirections.actionAddNewPatientFragmentToAddVisitFragment(patientData)
        controller.navigate(action)
    }

    private fun onFailure(){
        Toast.makeText(activity, "Something Went Wrong", Toast.LENGTH_SHORT).show()
    }

}