package com.samarthhms.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.samarthhms.R

class FindPatientByContactNumberFragment : Fragment() {

    companion object {
        fun newInstance() = FindPatientByContactNumberFragment()
    }

    private lateinit var viewModel: FindPatientByContactNumberViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_find_patient_by_contact_number, container, false)
    }

}