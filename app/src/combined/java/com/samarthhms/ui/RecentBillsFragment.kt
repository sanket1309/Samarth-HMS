package com.samarthhms.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.samarthhms.databinding.FragmentRecentBillsBinding
import com.samarthhms.models.Bill
import com.samarthhms.utils.StringUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecentBillsFragment : Fragment(), RecyclerOnItemViewClickListener {

    private val viewModel: RecentBillsViewModel by viewModels()

    private lateinit var binding: FragmentRecentBillsBinding

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRecentBillsBinding.inflate(layoutInflater, container, false)
        viewModel.updateData()
        val adapter = RecentBillAdapter(context, this, listOf())
        binding.recentBillsRecyclerView.adapter = adapter
        binding.noResultsImage.visibility = View.GONE
        viewModel.recentBillsList.observe(viewLifecycleOwner){
            (binding.recentBillsRecyclerView.adapter as RecentBillAdapter).bills = it
            binding.recentBillsTitle.text = StringUtils.getShowingBillsText(it.size)
            (binding.recentBillsRecyclerView.adapter as RecentBillAdapter).notifyDataSetChanged()
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
        if(data is Bill){
            val action = RecentBillsFragmentDirections.actionRecentBillsFragmentToEditBillFragment(data)
            findNavController().navigate(action)
        }
    }
}
