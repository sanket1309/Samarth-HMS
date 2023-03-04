package com.samarthhms.ui

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.room.util.StringUtil
import com.samarthhms.R
import com.samarthhms.databinding.FragmentPatientsTodayBinding
import com.samarthhms.utils.StringUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PatientsTodayFragment : Fragment(), RecyclerOnItemViewClickListener{

    private val viewModel: PatientsTodayViewModel by viewModels()

    private lateinit var binding: FragmentPatientsTodayBinding

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPatientsTodayBinding.inflate(layoutInflater, container, false)
        viewModel.updateData()
        viewModel.addListener()
        val adapter = VisitInfoAdapter(context, this, listOf())
        binding.patientsTodayRecyclerView.adapter = adapter
        viewModel.patientsTodayList.observe(viewLifecycleOwner){
            (binding.patientsTodayRecyclerView.adapter as VisitInfoAdapter).patientsToday = it
            (binding.patientsTodayRecyclerView.adapter as VisitInfoAdapter).notifyDataSetChanged()
        }
        viewModel.patientsTodayCount.observe(viewLifecycleOwner){
            binding.patientsTodayCountTitle.text = StringUtils.getPatientCountText(it)
        }
        return binding.root
    }

    override fun onItemClicked(data: Any) {
    }

}