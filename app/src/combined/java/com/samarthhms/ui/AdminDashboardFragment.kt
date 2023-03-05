package com.samarthhms.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.samarthhms.R
import com.samarthhms.constants.LoggedState
import com.samarthhms.constants.Role
import com.samarthhms.constants.SchemaName
import com.samarthhms.databinding.FragmentAdminDashboardBinding
import com.samarthhms.databinding.PatientInfoLayoutBinding
import com.samarthhms.databinding.VisitInfoLayoutBinding
import com.samarthhms.domain.LoginStatusResponse
import com.samarthhms.models.Patient
import com.samarthhms.models.PatientVisitInfo
import com.samarthhms.models.VisitFirebase
import com.samarthhms.navigator.Navigator
import com.samarthhms.repository.StoredStateDao
import com.samarthhms.utils.DateTimeUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Period
import javax.inject.Inject

@AndroidEntryPoint
class AdminDashboardFragment : Fragment(),RecyclerOnItemViewClickListener {
    private lateinit var binding: FragmentAdminDashboardBinding

    private val viewModel: AdminDashboardViewModel by viewModels()

    @Inject
    lateinit var storedStateDao: StoredStateDao

    @Inject
    lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAdminDashboardBinding.inflate(layoutInflater, container, false)
        binding.extendedFab.setOnClickListener{
            val controller = findNavController()
            controller.navigate(R.id.action_adminDashboardFragment_to_addPatientFragment)
        }
        viewModel.updateData()
        viewModel.addListener()
        val adapter = VisitInfoAdapter(context, this, listOf())
        binding.patientsTodayRecyclerView.adapter = adapter
        viewModel.patientsTodayList.observe(viewLifecycleOwner){
            (binding.patientsTodayRecyclerView.adapter as VisitInfoAdapter).patientsToday = it
            (binding.patientsTodayRecyclerView.adapter as VisitInfoAdapter).notifyDataSetChanged()
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

        binding.patientsTodayCountTitle.setOnClickListener{
            findNavController().navigate(R.id.action_adminDashboardFragment_to_patientsTodayFragment)
        }
        
        return binding.root
    }

    override fun onItemClicked(data: Any) {
    }

}