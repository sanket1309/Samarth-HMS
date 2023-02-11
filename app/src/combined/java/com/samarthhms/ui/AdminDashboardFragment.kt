package com.samarthhms.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.samarthhms.R
import com.samarthhms.constants.SchemaName
import com.samarthhms.databinding.FragmentAdminDashboardBinding
import com.samarthhms.databinding.PatientInfoLayoutBinding
import com.samarthhms.models.Patient
import com.samarthhms.models.PatientFirebase
import com.samarthhms.utils.DateTimeUtils
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Period

@AndroidEntryPoint
class AdminDashboardFragment : Fragment() {
    private lateinit var binding: FragmentAdminDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAdminDashboardBinding.inflate(layoutInflater, container, false)
        binding.extendedFab.setOnClickListener{
            val controller = findNavController()
            controller.navigate(R.id.action_adminDashboardFragment_to_addPatientFragment)
        }

        try{
            val db = FirebaseFirestore.getInstance()
            val patientsRef = db.collection(SchemaName.PATIENTS_COLLECTION)
            val query = patientsRef.orderBy("patientId",Query.Direction.DESCENDING)
//        val query = patientsRef.whereGreaterThanOrEqualTo("visitT",startTime).orderBy("entryTime",Query.Direction.DESCENDING)
            val options = FirestoreRecyclerOptions.Builder<Patient>()
                .setQuery(query) { snapshot ->
                    convertToPatient(snapshot.toObject(PatientFirebase::class.java)!!)
                }
                .setLifecycleOwner(this)
                .build()
            val adapter = PatientFirestoreRecyclerAdapter(options)
            val recyclerView = binding.patientsTodayRecyclerView
            recyclerView.adapter = adapter
            val patientsTodayCount = binding.patientsTodayCountNumber
//        greeting = view.findViewById(R.id.greeting)
//        name = view.findViewById(R.id.name)
//            updatePatientCount(adapter.itemCount, patientsTodayCount)
        }catch (e: Exception){
            Log.e("","SANKET ERROR $e")
        }

        return binding.root
    }

    private fun updatePatientCount(itemCount : Int, textView: TextView){
        textView.text = itemCount.toString()
    }

    private fun convertToPatient(patientFirebase: PatientFirebase): Patient{
        return Patient(
            patientFirebase.patientId,
            patientFirebase.firstName,
            patientFirebase.middleName,
            patientFirebase.lastName,
            patientFirebase.gender,
            patientFirebase.contactNumber,
            DateTimeUtils.getLocalDateTime(patientFirebase.dateOfBirth),
            patientFirebase.town,
            patientFirebase.taluka,
            patientFirebase.district
        )
    }

    private inner class PatientHolder internal constructor(private val patientInfoLayoutBinding: PatientInfoLayoutBinding) : RecyclerView.ViewHolder(patientInfoLayoutBinding.root) {
        fun bind(patient: Patient) {
            patientInfoLayoutBinding.patientId.text = patient.patientId
            patientInfoLayoutBinding.patientName.text = patient.firstName + " " + patient.lastName
            patientInfoLayoutBinding.patientGender.text = patient.gender.value + ", "
            patientInfoLayoutBinding.patientAge.text = getAgeText(DateTimeUtils.getTimestamp(patient.dateOfBirth))
            patientInfoLayoutBinding.patientAddress.text = patient.town + ", Tal." + patient.taluka
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

    private inner class PatientFirestoreRecyclerAdapter internal constructor(options: FirestoreRecyclerOptions<Patient>) : FirestoreRecyclerAdapter<Patient, PatientHolder>(options) {
        override fun onBindViewHolder(patientHolder: PatientHolder, position: Int, patient: Patient) {
            patientHolder.bind(patient)
        }

//        override fun getItemViewType(position: Int): Int {
//            return (itemCount-position-1)%3
//        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PatientHolder {
            val patientInfoLayoutBinding = PatientInfoLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return PatientHolder(patientInfoLayoutBinding)
        }

        @SuppressLint("NotifyDataSetChanged")
        override fun onDataChanged() {
            binding.patientsTodayRecyclerView.adapter?.notifyDataSetChanged()
            updatePatientCount(itemCount, binding.patientsTodayCountNumber)
        }
    }

}