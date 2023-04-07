package com.samarthhms.staff.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.samarthhms.R
import com.samarthhms.databinding.FragmentAddPatientBinding

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddPatientFragment : Fragment() {
    private lateinit var binding: FragmentAddPatientBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddPatientBinding.inflate(layoutInflater, container, false)
        binding.findByPatientIdButton.setOnClickListener{
            val controller = findNavController()
            controller.navigate(R.id.action_addPatientFragment_to_findPatientByPatientIdFragment)
        }
        binding.findByNameButton.setOnClickListener{
            val controller = findNavController()
            controller.navigate(R.id.action_addPatientFragment_to_findPatientByNameFragment)
        }
        binding.findByContactNumberButton.setOnClickListener{
            val controller = findNavController()
            controller.navigate(R.id.action_addPatientFragment_to_findPatientByContactNumberFragment)
        }
        binding.addNewPatientButton.setOnClickListener{
            val controller = findNavController()
            controller.navigate(R.id.action_addPatientFragment_to_addNewPatientFragment)
        }
        return binding.root
    }
}