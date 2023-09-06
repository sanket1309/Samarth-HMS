package com.samarthhms.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.samarthhms.constants.Constants
import com.samarthhms.databinding.FragmentAdminSettingsBinding
import com.samarthhms.domain.Status
import com.samarthhms.models.AdminDetails
import com.samarthhms.navigator.Navigator
import com.samarthhms.utils.RecyclerViewAdapterUtils
import com.samarthhms.utils.ToastUtils
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AdminSettingsFragment : Fragment(), RecyclerOnItemViewClickListener {

    private val viewModel: AdminSettingsViewModel by viewModels()

    private lateinit var binding: FragmentAdminSettingsBinding

    @Inject
    lateinit var navigator: Navigator

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAdminSettingsBinding.inflate(layoutInflater, container, false)
        startProgressBar(false)
        binding.recyclerView.adapter = AdminDetailsAdapter(this, listOf())
        viewModel.admin.observe(viewLifecycleOwner) { onAdminsFetched(it) }
        viewModel.getAdminStatus.observe(viewLifecycleOwner) { onGetAdminStatus(it) }
        binding.addAdminButton.setOnClickListener { onAdminSelected() }
        viewModel.getAllAdmins()
        return binding.root
    }

    private fun onAdminSelected(){
        navigator.navigateToFragment(this,AdminSettingsFragmentDirections.actionAdminSettingsFragmentToAddAdminFragment())
    }

    private fun onAdminsFetched(adminsDetails: List<AdminDetails>){
        if (adminsDetails.isNotEmpty()) { startProgressBar(false) }
        RecyclerViewAdapterUtils.updateData<AdminDetailsAdapter.AdminHolder, AdminDetails>(binding.recyclerView.adapter, adminsDetails)
    }

    private fun onGetAdminStatus(status: Status){
        if (status == Status.SUCCESS) {
            startProgressBar(false)
        } else if (status == Status.FAILURE) {
            ToastUtils.showToast(requireContext(), Constants.Messages.SOMETHING_WENT_WRONG)
            startProgressBar(false)
        }
    }

    override fun onItemClicked(data: Any?, requester: String) {
        if (data is AdminDetails) {
            navigator.navigateToFragment(this,AdminSettingsFragmentDirections.actionAdminSettingsFragmentToAdminDetailsFragment(data))
        }
    }

    fun startProgressBar(isVisible: Boolean) {
        (activity as MainActivity).startProgressBar(isVisible)
    }
}

