package com.samarthhms.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.ktx.Firebase
import com.samarthhms.R
import com.samarthhms.constants.Gender
import com.samarthhms.constants.SchemaName
import com.samarthhms.databinding.FragmentAddPatientBinding

import com.samarthhms.databinding.FragmentAdminDashboardBinding
import com.samarthhms.models.Patient
import com.samarthhms.models.PatientFirebase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

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
//        binding.findByContactNumberButton.setOnClickListener{}
        binding.addNewPatientButton.setOnClickListener{
            val controller = findNavController()
            controller.navigate(R.id.action_addPatientFragment_to_addNewPatientFragment)
        }
        return binding.root
    }
}