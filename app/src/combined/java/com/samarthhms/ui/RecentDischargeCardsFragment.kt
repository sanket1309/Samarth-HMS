package com.samarthhms.ui

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.samarthhms.databinding.FragmentRecentDischargeCardsBinding
import com.samarthhms.models.DischargeCard
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
            (binding.recentDischargeCardsRecyclerView.adapter as RecentDischargeCardAdapter).dischargeCards = it
            binding.recentDischargeCardsTitle.text = StringUtils.getShowingDischargeCardsText(it.size)
            (binding.recentDischargeCardsRecyclerView.adapter as RecentDischargeCardAdapter).notifyDataSetChanged()
            if(it.isEmpty()){
                binding.noResultsImage.visibility = View.VISIBLE
            }
            else{
                binding.noResultsImage.visibility = View.GONE
            }
        }
        return binding.root
    }

    override fun onItemClicked(data: Any?, requester: String, isLongPress: Boolean) {
        if(data is DischargeCard){
            if(isLongPress){
                val dialogClickListener = DialogInterface.OnClickListener{
                        dialog, which ->
                    when(which){
                        DialogInterface.BUTTON_POSITIVE -> {
                            data.ipdNumber = ""
                            val action = RecentDischargeCardsFragmentDirections.actionRecentDischargeCardsFragmentToEditDischargeCardFragment(data)
                            findNavController().navigate(action)
                        }
                        DialogInterface.BUTTON_NEGATIVE -> {
                            dialog.dismiss()
                        }
                    }
                }
                val alertDialogBuilder = AlertDialog.Builder(requireContext())
                alertDialogBuilder.setMessage("Make copy of this card?")
                    .setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener)
                    .show()

            }else{
                val action = RecentDischargeCardsFragmentDirections.actionRecentDischargeCardsFragmentToEditDischargeCardFragment(data)
                findNavController().navigate(action)
            }
        }
    }
}
