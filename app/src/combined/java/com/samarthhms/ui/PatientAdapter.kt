package com.samarthhms.ui

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Timestamp
import com.samarthhms.R
import com.samarthhms.databinding.PatientInfoLayoutBinding
import com.samarthhms.models.Patient
import com.samarthhms.utils.DateTimeUtils
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Period

class PatientAdapter internal constructor(var context: Context?, var recyclerOnItemViewClickListener: RecyclerOnItemViewClickListener, var patients: List<Patient>) : RecyclerView.Adapter<PatientAdapter.PatientHolder>() {
    override fun onBindViewHolder(patientHolder: PatientAdapter.PatientHolder, position: Int) {
        patientHolder.bind(patients[position])
    }

        override fun getItemViewType(position: Int): Int {
            return (itemCount-position-1)%3
        }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PatientAdapter.PatientHolder {
        val patientInfoLayoutBinding = PatientInfoLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val resources = context?.resources
        when(viewType){
            0 -> patientInfoLayoutBinding.infoBlock.background = resources?.getDrawable(R.drawable.patient_info_background_red,null)
            1 -> patientInfoLayoutBinding.infoBlock.background = resources?.getDrawable(R.drawable.patient_info_background_green,null)
            2 -> patientInfoLayoutBinding.infoBlock.background = resources?.getDrawable(R.drawable.patient_info_background_blue,null)
        }
        return PatientHolder(patientInfoLayoutBinding)
    }

    override fun getItemCount(): Int {
        return patients.size
    }

    inner class PatientHolder internal constructor(private val patientInfoLayoutBinding: PatientInfoLayoutBinding) : RecyclerView.ViewHolder(patientInfoLayoutBinding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(patient: Patient) {
            patientInfoLayoutBinding.patientId.text = patient.patientId
            patientInfoLayoutBinding.patientName.text = patient.firstName + " " + patient.lastName
            patientInfoLayoutBinding.patientGender.text = patient.gender.value + ", "
            patientInfoLayoutBinding.patientAge.text = getAgeText(DateTimeUtils.getTimestamp(patient.dateOfBirth))
            patientInfoLayoutBinding.patientAddress.text = patient.town + ", Tal." + patient.taluka
            patientInfoLayoutBinding.infoBlock.setOnClickListener{
                recyclerOnItemViewClickListener.onItemClicked(patient)
            }
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
}