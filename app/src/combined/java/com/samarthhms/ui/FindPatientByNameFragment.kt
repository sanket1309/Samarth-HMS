package com.samarthhms.ui

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.samarthhms.R
import com.samarthhms.databinding.FragmentFindPatientByContactNumberBinding
import com.samarthhms.databinding.FragmentFindPatientByNameBinding
import com.samarthhms.domain.FindPatientByName
import com.samarthhms.domain.Status
import com.samarthhms.models.Patient
import com.samarthhms.utils.StringUtils
import com.samarthhms.utils.Validation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FindPatientByNameFragment : Fragment(), RecyclerOnItemViewClickListener {

    private lateinit var binding: FragmentFindPatientByNameBinding

    private val viewModel: FindPatientByNameViewModel by viewModels()

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFindPatientByNameBinding.inflate(layoutInflater, container, false)
        val adapter = PatientAdapter(context, this, listOf())
        binding.patientsRecyclerView.adapter = adapter
        binding.searchButton.setOnClickListener{
            val firstName = binding.firstName.text.toString()
            val middleName = binding.middleName.text.toString()
            val lastName = binding.lastName.text.toString()
            if(firstName.isBlank() && middleName.isBlank() && lastName.isBlank()){
                Toast.makeText(activity, "All names cant be empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(!Validation.validateName(lastName)){
                Toast.makeText(activity, "Last name is invalid", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(!Validation.validateName(firstName) && !Validation.validateName(middleName)){
                Toast.makeText(activity, "Enter atleast firstname or middlename", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            viewModel.getPatients(firstName, middleName, lastName)
        }
        viewModel.patientsList.observe(viewLifecycleOwner){
            val patients = it
            val status = viewModel.getPatientsStatus.value
            if(status == Status.SUCCESS){
                binding.resultText.text = StringUtils.getResultFoundText(patients!!.size)
                (binding.patientsRecyclerView.adapter as PatientAdapter).patients = patients
                (binding.patientsRecyclerView.adapter as PatientAdapter).notifyDataSetChanged()
            }
            else if(status == Status.FAILURE){
                binding.resultText.text = StringUtils.getResultFoundText(0)
                (binding.patientsRecyclerView.adapter as PatientAdapter).patients = listOf()
                (binding.patientsRecyclerView.adapter as PatientAdapter).notifyDataSetChanged()
                Toast.makeText(activity, "Something went wrong", Toast.LENGTH_SHORT).show()
            }
        }
        return binding.root
    }

    override fun onItemClicked(data: Any) {
        if(data is Patient){
            val action = FindPatientByNameFragmentDirections.actionFindPatientByNameFragmentToAddVisitFragment(data)
            findNavController().navigate(action)
        }
    }

}