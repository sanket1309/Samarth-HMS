package com.samarthhms.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.samarthhms.R
import com.samarthhms.constants.LoggedState
import com.samarthhms.constants.Role
import com.samarthhms.constants.SchemaName
import com.samarthhms.databinding.FragmentAdminDashboardBinding
import com.samarthhms.databinding.PatientInfoLayoutBinding
import com.samarthhms.databinding.VisitInfoLayoutBinding
import com.samarthhms.domain.LoginStatusResponse
import com.samarthhms.models.Patient
import com.samarthhms.models.PatientVisitInfo
import com.samarthhms.models.VisitFirebase
import com.samarthhms.navigator.Navigator
import com.samarthhms.repository.StoredStateDao
import com.samarthhms.utils.DateTimeUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Period
import javax.inject.Inject

@AndroidEntryPoint
class AdminDashboardFragment : Fragment() {
    private lateinit var binding: FragmentAdminDashboardBinding

    private val viewModel: AdminDashboardViewModel by viewModels()

    @Inject
    lateinit var storedStateDao: StoredStateDao

    @Inject
    lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAdminDashboardBinding.inflate(layoutInflater, container, false)
        binding.extendedFab.setOnClickListener{
            val controller = findNavController()
            controller.navigate(R.id.action_adminDashboardFragment_to_addPatientFragment)
        }
        viewModel.updateData()
        viewModel.addListener()
        val adapter = PatientAdapter(listOf())
        binding.patientsTodayRecyclerView.adapter = adapter
        viewModel.patientsTodayList.observe(viewLifecycleOwner){
            (binding.patientsTodayRecyclerView.adapter as PatientAdapter).patientsToday = it
            (binding.patientsTodayRecyclerView.adapter as PatientAdapter).notifyDataSetChanged()
        }
        viewModel.patientsTodayCount.observe(viewLifecycleOwner){
            binding.patientsTodayCountNumber.text = it.toString()
        }
        viewModel.unattendedPatientsCount.observe(viewLifecycleOwner){
            binding.unattendedPatientsCountNumber.text = it.toString()
        }
        viewModel.admitPatientsCount.observe(viewLifecycleOwner){
            binding.admitPatientsCountNumber.text = it.toString()
        }

        binding.patientsTodayCount.setOnClickListener{
            GlobalScope.launch {
                storedStateDao.delete(SchemaName.STORED_STATE_KEY)
                navigator.showMain(requireContext(), LoginStatusResponse(Role.ADMIN, LoggedState.LOGGED_OUT))
            }
        }

        binding.unattendedPatientsCount.setOnClickListener{
            GlobalScope.launch {
                val db = FirebaseFirestore.getInstance()
                val doc = db.collection(SchemaName.VISITS_COLLECTION).document()
                val visit = VisitFirebase(
                    doc.id,
                    "SBP00005",
                    "rJmBWYee8TqOjwId1cF1",
                    "rJmBWYee8TqOjwId1cF1",
                    Role.ADMIN
                )
                doc.set(visit)
            }
        }

        return binding.root
    }

    private fun updatePatientCount(itemCount : Int, textView: TextView){
        textView.text = itemCount.toString()
    }

    private inner class PatientHolder internal constructor(private val visitInfoLayoutBinding: VisitInfoLayoutBinding) : RecyclerView.ViewHolder(visitInfoLayoutBinding.root) {
        fun bind(patientVisitInfo: PatientVisitInfo) {
            val patient = patientVisitInfo.patient
            visitInfoLayoutBinding.patientId.text = patient.patientId
            visitInfoLayoutBinding.patientName.text = patient.firstName + " " + patient.lastName
            visitInfoLayoutBinding.patientGender.text = patient.gender.value + ", "
            visitInfoLayoutBinding.patientAge.text = getAgeText(DateTimeUtils.getTimestamp(patient.dateOfBirth))
            visitInfoLayoutBinding.patientAddress.text = patient.town + ", Tal." + patient.taluka
            visitInfoLayoutBinding.visitTime.text = getDisplayTime(DateTimeUtils.getTimestamp(patientVisitInfo.visitTime))
            visitInfoLayoutBinding.infoBlock.setOnClickListener{
                (activity as MainActivity).data = patient
                val action = AdminDashboardFragmentDirections.actionAdminDashboardFragmentToAddVisitFragment(patient)
                findNavController().navigate(action)
            }
        }

        fun getDisplayTime(time : Timestamp) :String{
            val sfd = SimpleDateFormat("hh:mm aa")
            var timeStr = sfd.format(time.toDate()).toString()
            timeStr = timeStr.subSequence(0,6).toString() + timeStr.subSequence(6,8).toString().uppercase()
            if(timeStr.first() == '0') timeStr = " "+timeStr.substring(1)
            return timeStr
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

    private inner class PatientAdapter internal constructor(var patientsToday: List<PatientVisitInfo>) : RecyclerView.Adapter<PatientHolder>() {
        override fun onBindViewHolder(patientHolder: PatientHolder, position: Int) {
            patientHolder.bind(patientsToday[position])
        }

//        override fun getItemViewType(position: Int): Int {
//            return (itemCount-position-1)%3
//        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PatientHolder {
            val visitInfoLayoutBinding = VisitInfoLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return PatientHolder(visitInfoLayoutBinding)
        }

        override fun getItemCount(): Int {
            return patientsToday.size
        }
    }

}