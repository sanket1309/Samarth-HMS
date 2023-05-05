package com.samarthhms.ui

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.FirebaseFirestore
import com.samarthhms.R
import com.samarthhms.constants.SchemaName
import com.samarthhms.databinding.FragmentPatientsTodayBinding
import com.samarthhms.databinding.FragmentRecentBillsBinding
import com.samarthhms.domain.GetRecentBills
import com.samarthhms.domain.Status
import com.samarthhms.models.Bill
import com.samarthhms.models.PatientVisitInfo
import com.samarthhms.usecase.UseCase
import com.samarthhms.utils.StringUtils
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@AndroidEntryPoint
class RecentBillsFragment : Fragment(), RecyclerOnItemViewClickListener {

    private val viewModel: RecentBillsViewModel by viewModels()

    private lateinit var binding: FragmentRecentBillsBinding

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRecentBillsBinding.inflate(layoutInflater, container, false)
        viewModel.updateData()
        val adapter = RecentBillAdapter(context, this, listOf())
        binding.recentBillsRecyclerView.adapter = adapter
        viewModel.recentBillsList.observe(viewLifecycleOwner){
            (binding.recentBillsRecyclerView.adapter as RecentBillAdapter).bills = it
            binding.recentBillsTitle.text = StringUtils.getShowingBillsText(it.size)
            (binding.recentBillsRecyclerView.adapter as RecentBillAdapter).notifyDataSetChanged()
        }
        return binding.root
    }

    override fun onItemClicked(data: Any?, requester: String) {
        if(data is Bill){
            val action = RecentBillsFragmentDirections.actionRecentBillsFragmentToEditBillFragment(data)
            findNavController().navigate(action)
        }
    }
}
