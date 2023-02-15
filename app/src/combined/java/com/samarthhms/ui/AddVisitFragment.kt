package com.samarthhms.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.room.util.StringUtil
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.kotlinModule
import com.samarthhms.R
import com.samarthhms.databinding.FragmentAddNewPatientBinding
import com.samarthhms.databinding.FragmentAddPatientBinding
import com.samarthhms.databinding.FragmentAddVisitBinding
import com.samarthhms.domain.Status
import com.samarthhms.models.Patient
import com.samarthhms.navigator.Navigator
import com.samarthhms.utils.DateTimeUtils
import com.samarthhms.utils.StringUtils
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
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
    ): View? {
        binding = FragmentAddVisitBinding.inflate(layoutInflater, container, false)
        val patient = AddVisitFragmentArgs.fromBundle(requireArguments()).patient

        binding.patientId.setText(patient.patientId)
        binding.firstName.setText(patient.firstName)
        binding.middleName.setText(patient.middleName)
        binding.lastName.setText(patient.lastName)
        binding.gender.setText(patient.gender.value)
        binding.dateOfBirth.setText(DateTimeUtils.getDate(patient.dateOfBirth))
        binding.contactNumber.setText(patient.contactNumber)
        binding.town.setText(patient.town)
        binding.taluka.setText(patient.taluka)
        binding.district.setText(patient.district)

        binding.addVisitButton.setOnClickListener{
            addVisitViewModel.addVisit(patient)
        }

        addVisitViewModel.addVisitStatus.observe(viewLifecycleOwner){
            when(it){
                Status.SUCCESS -> onSuccess()
                Status.FAILURE -> onFailure()
                else -> {}
            }
        }

        return binding.root
    }

    private fun onSuccess(){
        Toast.makeText(activity, "Added Visit Successfully", Toast.LENGTH_SHORT).show()
        val controller = findNavController()
        val action = AddVisitFragmentDirections.actionAddVisitFragmentToAdminDashboardFragment()
        controller.navigate(action)
    }

    private fun onFailure(){
        Toast.makeText(activity, "Something Went Wrong", Toast.LENGTH_SHORT).show()
    }

}