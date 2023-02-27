package com.samarthhms.ui


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.firebase.Timestamp
import com.samarthhms.databinding.FragmentFindPatientByPatientIdBinding
import com.samarthhms.domain.Status
import com.samarthhms.models.Patient
import com.samarthhms.utils.DateTimeUtils
import com.samarthhms.utils.IdUtils
import com.samarthhms.utils.StringUtils
import com.samarthhms.utils.Validation
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Period
import java.util.Objects

@AndroidEntryPoint
class FindPatientByPatientIdFragment : Fragment() {

    private lateinit var binding: FragmentFindPatientByPatientIdBinding

    private val viewModel: FindPatientByPatientIdViewModel by viewModels()

    private var selectedPatient: Patient? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFindPatientByPatientIdBinding.inflate(layoutInflater, container, false)
        binding.searchButton.setOnClickListener{
            val patientId = IdUtils.PATIENT_ID_PREFIX + binding.patientId.text
            if(!Validation.validatePatientId(patientId)){
                Toast.makeText(activity, "Invalid Patient ID",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            viewModel.findPatient(patientId)
        }

        binding.patientInfo.root.setOnClickListener{
            if(selectedPatient != null){
                val action = FindPatientByPatientIdFragmentDirections.actionFindPatientByPatientIdFragmentToAddVisitFragment(selectedPatient!!)
                findNavController().navigate(action)
            }
        }

        viewModel.searchStatus.observe(viewLifecycleOwner){
            val patientInfoLayout = binding.patientInfo
            if(it == Status.SUCCESS){
                val patient = viewModel.patient.value
                selectedPatient = patient
                if(patient == null){
                    patientInfoLayout.root.visibility = GONE
                    binding.resultText.text = StringUtils.getResultFoundText(0)
                    return@observe
                }
                patientInfoLayout.patientId.text = patient.patientId
                patientInfoLayout.patientName.text = patient.firstName + patient.lastName
                patientInfoLayout.patientAddress.text = patient.town +" Tal."+ patient.taluka
                patientInfoLayout.patientGender.text = patient.gender.value+", "
                patientInfoLayout.patientAge.text = getAgeText(DateTimeUtils.getTimestamp(patient.dateOfBirth))
                binding.resultText.text = StringUtils.getResultFoundText(1)
                patientInfoLayout.root.visibility = VISIBLE
                return@observe
            }
            else if(it == Status.FAILURE){
                binding.resultText.text = StringUtils.getResultFoundText(0)
                Toast.makeText(activity, "Something went wrong", Toast.LENGTH_SHORT).show()
            }
            patientInfoLayout.root.visibility = GONE
        }
        return binding.root
    }

    fun getAgeText(dob: Timestamp): String{
        val formattedDate = SimpleDateFormat("ddMMyyyy").format(dob.toDate())
        val localDate = LocalDate.of(formattedDate.substring(4).toInt(),
            formattedDate.substring(2,4).toInt(),
            formattedDate.substring(0,2).toInt())
        val period = Period.between(localDate, LocalDate.now())
        val years = if(period.years <= 9) " ${period.years}" else period.years.toString()
        val months = if(period.months <= 9) " ${period.months}" else period.months.toString()
        return "${years}y ${months}m"
    }

}