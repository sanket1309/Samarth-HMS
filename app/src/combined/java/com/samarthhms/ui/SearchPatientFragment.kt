package com.samarthhms.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.samarthhms.constants.Constants
import com.samarthhms.databinding.FragmentFindPatientByNameBinding
import com.samarthhms.databinding.FragmentSearchPatientBinding
import com.samarthhms.domain.Status
import com.samarthhms.models.PatientVisitDetails
import com.samarthhms.models.PatientVisitInfo
import com.samarthhms.utils.*
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class SearchPatientFragment : Fragment(), RecyclerOnItemViewClickListener {

    private lateinit var binding: FragmentSearchPatientBinding

    private val viewModel: SearchPatientViewModel by viewModels()

    private var areFiltersHidden = false

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchPatientBinding.inflate(layoutInflater, container, false)
        binding.patientsRecyclerView.adapter = PatientAdapter(context, this, listOf())
        binding.searchButton.setOnClickListener{ onSearch() }
        binding.noResultsImage.visibility = View.VISIBLE
        binding.advancedFilter.setOnClickListener { onAdvancedFilter() }
        viewModel.patientsList.observe(viewLifecycleOwner){
            val patients = it
            val status = viewModel.getPatientsStatus.value
            if(status == Status.SUCCESS){
                binding.resultText.text = StringUtils.getResultFoundText(patients!!.size)
                RecyclerViewAdapterUtils.updateData<VisitInfoAdapter.VisitInfoHolder, PatientVisitInfo>(binding.patientsRecyclerView.adapter, it)
                if(it.isEmpty()){
                    binding.noResultsImage.visibility = View.VISIBLE
                }
                else{
                    binding.noResultsImage.visibility = View.GONE
                }
            }
            else if(status == Status.FAILURE){
                binding.resultText.text = StringUtils.getResultFoundText(0)
                RecyclerViewAdapterUtils.updateData<VisitInfoAdapter.VisitInfoHolder, PatientVisitInfo>(binding.patientsRecyclerView.adapter, it)
                binding.noResultsImage.visibility = View.GONE
                ToastUtils.showToast(requireContext(), Constants.Messages.SOMETHING_WENT_WRONG)
            }
        }
        return binding.root
    }

    private fun onAdvancedFilter(){
        if(areFiltersHidden){
            binding.noResultsImage.visibility = View.VISIBLE
            binding.firstNameTitle.visibility = View.VISIBLE
            binding.firstName.visibility = View.VISIBLE
            binding.middleNameTitle.visibility = View.VISIBLE
            binding.middleName.visibility = View.VISIBLE
            binding.lastNameTitle.visibility = View.VISIBLE
            binding.lastName.visibility = View.VISIBLE
            binding.contactNumberTitle.visibility = View.VISIBLE
            binding.contactNumber.visibility = View.VISIBLE
            binding.townTitle.visibility = View.VISIBLE
            binding.town.visibility = View.VISIBLE
            binding.advancedFilter.text = "Hide Filters"
        }else{
            binding.noResultsImage.visibility = View.GONE
            binding.firstNameTitle.visibility = View.GONE
            binding.firstName.visibility = View.GONE
            binding.middleNameTitle.visibility = View.GONE
            binding.middleName.visibility = View.GONE
            binding.lastNameTitle.visibility = View.GONE
            binding.lastName.visibility = View.GONE
            binding.contactNumberTitle.visibility = View.GONE
            binding.contactNumber.visibility = View.GONE
            binding.townTitle.visibility = View.GONE
            binding.town.visibility = View.GONE
            binding.advancedFilter.text = "Show Filters"
        }
        areFiltersHidden = !areFiltersHidden
    }

    private fun onSearch(){
        val searchPatientRequest = UiDataExtractorUtils.getSearchPatient(binding.root)
        try{
            ValidationUtils.validateSearchNameRequest(searchPatientRequest, requireContext())
        }catch (e: Exception){
            return
        }
        viewModel.getPatients(searchPatientRequest)
    }

    override fun onItemClicked(data: Any?, requester: String) {
        if(data is PatientVisitDetails){
            val action = FindPatientByNameFragmentDirections.actionFindPatientByNameFragmentToAddVisitFragment(data)
            findNavController().navigate(action)
        }
    }

}