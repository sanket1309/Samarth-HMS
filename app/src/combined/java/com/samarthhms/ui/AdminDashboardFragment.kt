package com.samarthhms.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.samarthhms.R
import com.samarthhms.databinding.FragmentAdminDashboardBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AdminDashboardFragment : Fragment() {
    private lateinit var binding: FragmentAdminDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAdminDashboardBinding.inflate(layoutInflater, container, false)
        binding.extendedFab.setOnClickListener{
            val controller = findNavController()
            controller.navigate(R.id.action_adminDashboardFragment_to_addPatientFragment)
        }
        return binding.root
    }
}