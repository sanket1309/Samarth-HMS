package com.samarthhms.staff.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.samarthhms.databinding.FragmentFindPatientByContactNumberBinding
import com.samarthhms.staff.domain.Status
import com.samarthhms.staff.models.Patient
import com.samarthhms.staff.utils.StringUtils
import com.samarthhms.staff.utils.Validation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FindPatientByContactNumberFragment : Fragment(), RecyclerOnItemViewClickListener {

    private lateinit var binding: FragmentFindPatientByContactNumberBinding

    private val viewModel: FindPatientByContactNumberViewModel by viewModels()

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFindPatientByContactNumberBinding.inflate(layoutInflater, container, false)
        val adapter = PatientAdapter(context, this, listOf())
        binding.patientsRecyclerView.adapter = adapter
        binding.searchButton.setOnClickListener{
            val contactNumber = binding.contactNumber.text.toString()
            if(!Validation.validateContactNumber(contactNumber)){
                Toast.makeText(activity, "Invalid Contact Number", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            viewModel.getPatients(contactNumber)
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

    override fun onItemClicked(data: Any?, requester: String) {
        if(data is Patient){
            val action = FindPatientByContactNumberFragmentDirections.actionFindPatientByContactNumberFragmentToAddVisitFragment(data)
            findNavController().navigate(action)
        }
    }

}