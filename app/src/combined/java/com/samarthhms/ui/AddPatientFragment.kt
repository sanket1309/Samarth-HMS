package com.samarthhms.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.samarthhms.R
import com.samarthhms.databinding.FragmentAddPatientBinding

import com.samarthhms.databinding.FragmentAdminDashboardBinding
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
//        binding.findByPatientIdButton.setOnClickListener{changeButtonTheme(it as Button, true)}
//        binding.findByNameButton.setOnClickListener{changeButtonTheme(it as Button, true)}
//        binding.findByContactNumberButton.setOnClickListener{changeButtonTheme(it as Button, true)}
//        binding.addNewPatientButton.setOnClickListener{changeButtonTheme(it as Button, true)}
        return binding.root
    }

    private fun changeButtonTheme(button: Button, isSelected : Boolean){
        if(isSelected){
            button.setTextColor(resources.getColor(R.color.white,null))
            button.setBackgroundColor(resources.getColor(R.color.blue_theme,null))
        }else{
            button.setTextColor(resources.getColor(R.color.blue_theme,null))
            button.setBackgroundColor(resources.getColor(R.color.white,null))
        }
    }
}