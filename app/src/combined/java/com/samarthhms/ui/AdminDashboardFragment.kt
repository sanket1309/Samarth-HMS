package com.samarthhms.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.navigation.NavigationView
import com.samarthhms.R
import com.samarthhms.databinding.FragmentAdminDashboardBinding
import com.samarthhms.models.*
import com.samarthhms.navigator.Navigator
import com.samarthhms.repository.AdminRepositoryImpl
import com.samarthhms.repository.StoredStateRepositoryImpl
import com.samarthhms.service.GenerateBill
import com.samarthhms.service.GenerateDischargeCard
import com.samarthhms.utils.EditableUtils
import com.samarthhms.utils.RecyclerViewAdapterUtils
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class AdminDashboardFragment : Fragment(),RecyclerOnItemViewClickListener {

    private lateinit var binding: FragmentAdminDashboardBinding

    private val viewModel: AdminDashboardViewModel by viewModels()

    @Inject
    lateinit var navigator: Navigator

    @Inject
    lateinit var generateDischargeCard: GenerateDischargeCard

    @Inject
    lateinit var generateBill: GenerateBill

    @Inject
    lateinit var adminRepository: AdminRepositoryImpl

    @Inject
    lateinit var storedStateRepository: StoredStateRepositoryImpl

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as MainActivity).binding.bottomNavigation.visibility = NavigationView.VISIBLE
        binding = FragmentAdminDashboardBinding.inflate(layoutInflater, container, false)
        startProgressBar(true)
        viewModel.updateGreetings()
        viewModel.updateData()
        viewModel.addListener()

        EditableUtils.updateVisibility(isVisible = false,binding.noResultsImage)
        binding.extendedFab.setOnClickListener{ onFabPressed() }
        binding.patientsTodayRecyclerView.adapter =  VisitInfoAdapter(context, this, listOf())
        viewModel.patientsTodayList.observe(viewLifecycleOwner){ onPatientsTodayListFetched(it) }
        viewModel.patientsTodayCount.observe(viewLifecycleOwner){ onPatientsTodayCountFetched(it) }
        viewModel.unattendedPatientsCount.observe(viewLifecycleOwner){ onUnattendedPatientsCountFetched(it) }
        viewModel.admitPatientsCount.observe(viewLifecycleOwner){ onAdmitPatientsCountFetched(it) }
        viewModel.greeting.observe(viewLifecycleOwner){ onGreetingFetched(it) }
        viewModel.userName.observe(viewLifecycleOwner){ onUserNameFetched(it) }
        binding.patientsTodayCountTitle.setOnClickListener{ onPatientsTodayCountPressed() }

        return binding.root
    }

    private fun onPatientsTodayCountPressed(){
        navigator.navigateToFragment(this, R.id.action_adminDashboardFragment_to_patientsTodayFragment)
    }

    private fun onFabPressed(){
        navigator.navigateToFragment(this, R.id.action_adminDashboardFragment_to_addPatientFragment)
    }

    private fun onPatientsTodayListFetched(visitsToday: List<PatientVisitInfo>){
        RecyclerViewAdapterUtils.updateData<VisitInfoAdapter.VisitInfoHolder, PatientVisitInfo>(binding.patientsTodayRecyclerView.adapter, visitsToday)
        EditableUtils.updateVisibility(isVisible = visitsToday.isEmpty(), binding.noResultsImage)
    }

    private fun onPatientsTodayCountFetched(count: Int){
        binding.patientsTodayCountNumber.text = count.toString()
    }

    private fun onUnattendedPatientsCountFetched(count: Int){
        binding.unattendedPatientsCountNumber.text = count.toString()
    }

    private fun onAdmitPatientsCountFetched(count: Int){
        binding.admitPatientsCountNumber.text = count.toString()
    }

    private fun onGreetingFetched(greeting: String){
        binding.greeting.text = greeting
    }

    private fun onUserNameFetched(name: String){
        if(name.isNotBlank()){
            startProgressBar(false)
        }
        binding.adminName.text = name
    }

    override fun onItemClicked(data: Any?, requester: String) {
    }

    fun startProgressBar(isVisible: Boolean){
        (activity as MainActivity).startProgressBar(isVisible)
    }


}