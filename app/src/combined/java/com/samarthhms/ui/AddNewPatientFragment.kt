package com.samarthhms.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.samarthhms.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddNewPatientFragment : Fragment() {

    companion object {
        fun newInstance() = AddNewPatientFragment()
    }

    private lateinit var viewModel: AddNewPatientViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_new_patient, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AddNewPatientViewModel::class.java)
        // TODO: Use the ViewModel
    }

}