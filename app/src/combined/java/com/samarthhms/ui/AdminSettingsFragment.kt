package com.samarthhms.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.samarthhms.databinding.FragmentAdminSettingsBinding
import com.samarthhms.domain.Status
import com.samarthhms.models.AdminDetails
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AdminSettingsFragment : Fragment(), RecyclerOnItemViewClickListener {

    private val viewModel: AdminSettingsViewModel by viewModels()

    private lateinit var binding: FragmentAdminSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAdminSettingsBinding.inflate(layoutInflater, container, false)
        startProgressBar(false)
        val adapter = AdminDetailsAdapter(this, listOf())
        binding.recyclerView.adapter = adapter

        viewModel.admin.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                startProgressBar(false)
            }
            (binding.recyclerView.adapter as AdminDetailsAdapter).admins = it
            (binding.recyclerView.adapter as AdminDetailsAdapter).notifyItemRangeChanged(0, it.size)
        }

        viewModel.getAdminStatus.observe(viewLifecycleOwner) {
            if (it == Status.SUCCESS) {
                startProgressBar(false)
            } else if (it == Status.FAILURE) {
                Toast.makeText(activity, "Something Went Wrong", Toast.LENGTH_SHORT).show()
                startProgressBar(false)
            }
        }
        binding.addAdminButton.setOnClickListener {
            val action =
                AdminSettingsFragmentDirections.actionAdminSettingsFragmentToAddAdminFragment()
            findNavController().navigate(action)
        }
        viewModel.getAllAdmins()
        return binding.root
    }

    override fun onItemClicked(data: Any?, requester: String, isLongPress: Boolean) {
        if (data is AdminDetails) {
            val action = AdminSettingsFragmentDirections.actionAdminSettingsFragmentToAdminDetailsFragment(data)
            findNavController().navigate(action)
        }
    }

    fun startProgressBar(isVisible: Boolean) {
        binding.root.isClickable = !isVisible
        (activity as MainActivity).startProgressBar(isVisible)
    }
}

