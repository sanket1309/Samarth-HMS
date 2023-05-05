package com.samarthhms.ui

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.samarthhms.databinding.FragmentSwitchAdminBinding
import com.samarthhms.domain.Status
import com.samarthhms.models.SwitchAdminData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SwitchAdminFragment : Fragment(), RecyclerOnItemViewClickListener {

    private val viewModel: SwitchAdminViewModel by viewModels()

    private lateinit var binding: FragmentSwitchAdminBinding

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSwitchAdminBinding.inflate(layoutInflater, container, false)
        startProgressBar(false)
        val adapter = SwitchAdminAdapter(this, listOf())
        binding.recyclerView.adapter = adapter

        viewModel.admins.observe(viewLifecycleOwner){
            if(it.isNotEmpty()){
                startProgressBar(false)
            }
            Log.e("", "RESULT : $it")
            (binding.recyclerView.adapter as SwitchAdminAdapter).admins = it
            (binding.recyclerView.adapter as SwitchAdminAdapter).notifyItemRangeChanged(0,it.size)
        }

        viewModel.switchAdminStatus.observe(viewLifecycleOwner){
            if(it == Status.SUCCESS){
                startProgressBar(false)
            }
            else if(it == Status.FAILURE){
                Toast.makeText(activity, "Something Went Wrong", Toast.LENGTH_SHORT).show()
                startProgressBar(false)
            }
        }
        viewModel.getAdmins()
        return binding.root
    }

    override fun onItemClicked(data: Any?, requester: String) {
        if(data is SwitchAdminData){
            val dialogClickListener = DialogInterface.OnClickListener{
                    dialog, which ->
                when(which){
                    DialogInterface.BUTTON_POSITIVE -> {
                        startProgressBar(true)
                        viewModel.switchAdmin(data)
                    }
                    DialogInterface.BUTTON_NEGATIVE -> {
                    }
                }
            }
            val alertDialogBuilder = AlertDialog.Builder(requireActivity())
            alertDialogBuilder.setMessage("Are you sure, you want to switch to ${data.admin?.firstName}?")
                .setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener)
                .show()
        }
    }

    fun startProgressBar(isVisible: Boolean){
        binding.root.isClickable = !isVisible
        (activity as MainActivity).startProgressBar(isVisible)
    }

}