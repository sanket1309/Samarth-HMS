package com.samarthhms.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.firebase.Timestamp
import com.samarthhms.databinding.FragmentStaffHomeBinding
import com.samarthhms.navigator.Navigator
import com.samarthhms.utils.DateTimeUtils
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Period
import javax.inject.Inject

@AndroidEntryPoint
class StaffHomeFragment : Fragment() {

    @Inject
    lateinit var navigator: Navigator

    private val viewModel: StaffHomeViewModel by viewModels()

    private lateinit var binding: FragmentStaffHomeBinding

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStaffHomeBinding.inflate(layoutInflater, container, false)

        viewModel.updateData()
        viewModel.updateGreetingsAndInfo()
        binding.recentlyAdded.visibility = View.GONE
        binding.recentVisit.root.visibility = View.GONE
        binding.addPatientButton.setOnClickListener {
            val action = StaffHomeFragmentDirections.actionStaffHomeFragmentToAddPatientFragment()
            findNavController().navigate(action)
        }
        viewModel.greeting.observe(viewLifecycleOwner){
            binding.greeting.text = it
        }

        viewModel.userName.observe(viewLifecycleOwner){
            binding.adminName.text = it
        }

        viewModel.day.observe(viewLifecycleOwner){
            binding.day.text = it
        }

        viewModel.date.observe(viewLifecycleOwner){
            binding.date.text = it
        }

        viewModel.isLocked.observe(viewLifecycleOwner){
            if(it){
                val intent = Intent(context, StaffLockActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(intent)
            }
        }

        viewModel.recentVisit.observe(viewLifecycleOwner){
            if(it!=null) {
                binding.recentlyAdded.visibility = View.VISIBLE
                val view = binding.recentVisit
                view.patientId.text = it.patient.patientId
                view.patientName.text = it.patient.firstName+" "+it.patient.lastName
                view.patientAddress.text = it.patient.town+", Tal. "+it.patient.taluka
                view.patientGender.text = it.patient.gender.value+", "
                view.patientAge.text = getAgeText(DateTimeUtils.getTimestamp(it.patient.dateOfBirth))
                view.visitTime.text = getDisplayTime(DateTimeUtils.getTimestamp(it.visitTime))
                view.root.visibility = View.VISIBLE
            }else{
                binding.recentlyAdded.visibility = View.GONE
                binding.recentVisit.root.visibility = View.GONE
            }
        }
        return binding.root
    }

    @SuppressLint("SimpleDateFormat")
    fun getDisplayTime(time : Timestamp) :String{
        val sfd = SimpleDateFormat("hh:mm aa")
        var timeStr = sfd.format(time.toDate()).toString()
        timeStr = timeStr.subSequence(0,6).toString() + timeStr.subSequence(6,8).toString().uppercase()
        if(timeStr.first() == '0') timeStr = " "+timeStr.substring(1)
        return timeStr
    }

    @SuppressLint("SimpleDateFormat")
    private fun getAgeText(dob: Timestamp): String{
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