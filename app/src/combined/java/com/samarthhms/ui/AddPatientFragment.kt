package com.samarthhms.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.samarthhms.R
import com.samarthhms.databinding.FragmentAddPatientBinding
import com.samarthhms.navigator.Navigator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AddPatientFragment : Fragment() {
    private lateinit var binding: FragmentAddPatientBinding

    @Inject
    lateinit var navigator: Navigator

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddPatientBinding.inflate(layoutInflater, container, false)
        binding.findByPatientIdButton.setOnClickListener{ navigator.navigateToFragment(this, R.id.action_addPatientFragment_to_findPatientByPatientIdFragment) }
        binding.findByNameButton.setOnClickListener{ navigator.navigateToFragment(this, R.id.action_addPatientFragment_to_findPatientByNameFragment) }
        binding.findByContactNumberButton.setOnClickListener{ navigator.navigateToFragment(this, R.id.action_addPatientFragment_to_findPatientByContactNumberFragment) }
        binding.addNewPatientButton.setOnClickListener{ navigator.navigateToFragment(this, R.id.action_addPatientFragment_to_addNewPatientFragment) }
        return binding.root
    }
}