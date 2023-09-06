package com.samarthhms.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.samarthhms.databinding.FragmentRecentDischargeCardsBinding
import com.samarthhms.models.DischargeCard
import com.samarthhms.models.RecyclerViewAdapter
import com.samarthhms.utils.RecyclerViewAdapterUtils
import com.samarthhms.utils.StringUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecentDischargeCardsFragment : Fragment(), RecyclerOnItemViewClickListener {

    private val viewModel: RecentDischargeCardsViewModel by viewModels()

    private lateinit var binding: FragmentRecentDischargeCardsBinding

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRecentDischargeCardsBinding.inflate(layoutInflater, container, false)
        viewModel.updateData()
        val adapter = RecentDischargeCardAdapter(context, this, listOf())
        binding.recentDischargeCardsRecyclerView.adapter = adapter
        binding.noResultsImage.visibility = View.GONE
        viewModel.recentDischargeCardsList.observe(viewLifecycleOwner){
            RecyclerViewAdapterUtils.updateData<RecentDischargeCardAdapter.DischargeCardHolder,DischargeCard>(binding.recentDischargeCardsRecyclerView.adapter,it)
            binding.recentDischargeCardsTitle.text = StringUtils.getShowingDischargeCardsText(it.size)
            if(it.isEmpty()){
                binding.noResultsImage.visibility = View.VISIBLE
            }
            else{
                binding.noResultsImage.visibility = View.GONE
            }
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
