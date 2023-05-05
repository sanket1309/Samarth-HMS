package com.samarthhms.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
//import androidx.fragment.app.viewModels
import com.samarthhms.databinding.FragmentFileDetailsBinding

class FileDetailsFragment : Fragment() {

//    private val viewModel: FileDetailsViewModel by viewModels()

    private lateinit var binding: FragmentFileDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFileDetailsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

}