package com.samarthhms.ui

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.samarthhms.databinding.FragmentStaffSettingsBinding
import com.samarthhms.domain.Status
import com.samarthhms.models.StaffDetails
import com.samarthhms.models.StaffStatus
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StaffSettingsFragment : Fragment(), RecyclerOnItemViewClickListener {

    private val viewModel: StaffSettingsViewModel by viewModels()

    private lateinit var binding: FragmentStaffSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStaffSettingsBinding.inflate(layoutInflater, container, false)
        startProgressBar(false)
        val adapter = StaffAdapter(this, listOf())
        binding.recyclerView.adapter = adapter

        viewModel.staff.observe(viewLifecycleOwner){
            if(it.isNotEmpty()){
                startProgressBar(false)
            }
            (binding.recyclerView.adapter as StaffAdapter).staff = it
            (binding.recyclerView.adapter as StaffAdapter).notifyItemRangeChanged(0,it.size)
        }

        viewModel.getStaffStatus.observe(viewLifecycleOwner){
            if(it == Status.SUCCESS){
                startProgressBar(false)
            }
            else if(it == Status.FAILURE){
                Toast.makeText(activity, "Something Went Wrong", Toast.LENGTH_SHORT).show()
                startProgressBar(false)
            }
        }
        binding.addStaffButton.setOnClickListener{
            val action = StaffSettingsFragmentDirections.actionStaffSettingsFragmentToAddStaffFragment()
            findNavController().navigate(action)
        }
        viewModel.getAllStaff()
        return binding.root
    }

    override fun onItemClicked(data: Any?, requester: String, isLongPress: Boolean) {
        if(data is StaffDetails && requester == "LOCK"){
            val dialogClickListener = DialogInterface.OnClickListener{
                    _, which ->
                when(which){
                    DialogInterface.BUTTON_POSITIVE -> {
                        startProgressBar(true)
                        viewModel.updateLockStatus(StaffStatus(data.staffId, data.staffStatus!!.isLoggedIn, false))
                    }
                    DialogInterface.BUTTON_NEGATIVE -> {
                    }
                }
            }
            val alertDialogBuilder = AlertDialog.Builder(requireActivity())
            alertDialogBuilder.setMessage("Are you sure, you want to unlock account of ${data.staff?.firstName}?")
                .setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener)
                .show()
        }
        else if(data is StaffDetails && requester == "UNLOCK"){
            val dialogClickListener = DialogInterface.OnClickListener{
                    _, which ->
                when(which){
                    DialogInterface.BUTTON_POSITIVE -> {
                        startProgressBar(true)
                        viewModel.updateLockStatus(StaffStatus(data.staffId, data.staffStatus!!.isLoggedIn, true))
                    }
                    DialogInterface.BUTTON_NEGATIVE -> {
                    }
                }
            }
            val alertDialogBuilder = AlertDialog.Builder(requireActivity())
            alertDialogBuilder.setMessage("Are you sure, you want to lock account of ${data.staff?.firstName}?")
                .setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener)
                .show()
        }
        else if(data is StaffDetails){
            val action = StaffSettingsFragmentDirections.actionStaffSettingsFragmentToStaffDetailsFragment(data)
            findNavController().navigate(action)
        }
    }

    fun startProgressBar(isVisible: Boolean){
        binding.root.isClickable = !isVisible
        (activity as MainActivity).startProgressBar(isVisible)
    }

}