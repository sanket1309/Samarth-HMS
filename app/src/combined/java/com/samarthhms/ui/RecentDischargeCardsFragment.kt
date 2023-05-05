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
import com.samarthhms.R
import com.samarthhms.databinding.FragmentRecentBillsBinding
import com.samarthhms.databinding.FragmentRecentDischargeCardsBinding
import com.samarthhms.domain.GetRecentBills
import com.samarthhms.domain.GetRecentDischargeCards
import com.samarthhms.domain.Status
import com.samarthhms.models.Bill
import com.samarthhms.models.DischargeCard
import com.samarthhms.usecase.UseCase
import com.samarthhms.utils.StringUtils
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@AndroidEntryPoint
class RecentDischargeCardsFragment : Fragment(), RecyclerOnItemViewClickListener {

    private val viewModel: RecentDischargeCardsViewModel by viewModels()

    private lateinit var binding: FragmentRecentDischargeCardsBinding

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRecentDischargeCardsBinding.inflate(layoutInflater, container, false)
        viewModel.updateData()
        val adapter = RecentDischargeCardAdapter(context, this, listOf())
        binding.recentDischargeCardsRecyclerView.adapter = adapter
        viewModel.recentDischargeCardsList.observe(viewLifecycleOwner){
            (binding.recentDischargeCardsRecyclerView.adapter as RecentDischargeCardAdapter).dischargeCards = it
            binding.recentDischargeCardsTitle.text = StringUtils.getShowingDischargeCardsText(it.size)
            (binding.recentDischargeCardsRecyclerView.adapter as RecentDischargeCardAdapter).notifyDataSetChanged()
        }
        return binding.root
    }

    override fun onItemClicked(data: Any?, requester: String) {
        if(data is DischargeCard){
            val action = RecentDischargeCardsFragmentDirections.actionRecentDischargeCardsFragmentToEditDischargeCardFragment(data)
            findNavController().navigate(action)
        }
    }
}
