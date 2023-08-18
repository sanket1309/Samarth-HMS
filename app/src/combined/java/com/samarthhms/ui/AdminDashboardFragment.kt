package com.samarthhms.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.navigation.NavigationView
import com.samarthhms.R
import com.samarthhms.databinding.FragmentAdminDashboardBinding
import com.samarthhms.models.*
import com.samarthhms.navigator.Navigator
import com.samarthhms.repository.AdminRepositoryImpl
import com.samarthhms.repository.StoredStateRepositoryImpl
import com.samarthhms.service.GenerateBill
import com.samarthhms.service.GenerateDischargeCard
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
        binding.extendedFab.setOnClickListener{
            val controller = findNavController()
            controller.navigate(R.id.action_adminDashboardFragment_to_addPatientFragment)
        }
        viewModel.updateData()
        viewModel.addListener()
        binding.noResultsImage.visibility = View.GONE

        val adapter = VisitInfoAdapter(context, this, listOf())
        binding.patientsTodayRecyclerView.adapter = adapter
        viewModel.patientsTodayList.observe(viewLifecycleOwner){
            (binding.patientsTodayRecyclerView.adapter as VisitInfoAdapter).patientsToday = it
            (binding.patientsTodayRecyclerView.adapter as VisitInfoAdapter).notifyDataSetChanged()
            if(it.isEmpty()){
                binding.noResultsImage.visibility = View.VISIBLE
            }
            else{
                binding.noResultsImage.visibility = View.GONE
            }
        }
        viewModel.patientsTodayCount.observe(viewLifecycleOwner){
            binding.patientsTodayCountNumber.text = it.toString()
        }
        viewModel.unattendedPatientsCount.observe(viewLifecycleOwner){
            binding.unattendedPatientsCountNumber.text = it.toString()
        }
        viewModel.admitPatientsCount.observe(viewLifecycleOwner){
            binding.admitPatientsCountNumber.text = it.toString()
        }

        viewModel.greeting.observe(viewLifecycleOwner){
            binding.greeting.text = it.toString()
        }

        viewModel.userName.observe(viewLifecycleOwner){
            if(it.isNotBlank()){
                startProgressBar(false)
            }
            binding.adminName.text = it.toString()
        }

        binding.patientsTodayCountTitle.setOnClickListener{
            navigator.navigateToFragment(this, R.id.action_adminDashboardFragment_to_patientsTodayFragment)
        }

        return binding.root
    }

    override fun onItemClicked(data: Any?, requester: String) {
    }

    fun startProgressBar(isVisible: Boolean){
        binding.root.isClickable = !isVisible
        (activity as MainActivity).startProgressBar(isVisible)
    }


}