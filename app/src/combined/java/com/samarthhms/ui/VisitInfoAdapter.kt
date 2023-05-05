package com.samarthhms.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Timestamp
import com.samarthhms.R
import com.samarthhms.databinding.VisitInfoLayoutBinding
import com.samarthhms.models.PatientVisitInfo
import com.samarthhms.utils.DateTimeUtils
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Period

class VisitInfoAdapter internal constructor(var context: Context?,var recyclerOnItemViewClickListener: RecyclerOnItemViewClickListener, var patientsToday: List<PatientVisitInfo>) : RecyclerView.Adapter<VisitInfoAdapter.VisitInfoHolder>() {
    override fun onBindViewHolder(visitInfoHolder: VisitInfoAdapter.VisitInfoHolder, position: Int) {
        visitInfoHolder.bind(patientsToday[position])
    }

        override fun getItemViewType(position: Int): Int {
            return (itemCount-position-1)%3
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VisitInfoAdapter.VisitInfoHolder {
        val visitInfoLayoutBinding = VisitInfoLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val resources = context?.resources
        when(viewType){
            0 -> visitInfoLayoutBinding.infoBlock.background = resources?.getDrawable(R.drawable.patient_info_background_red,null)
            1 -> visitInfoLayoutBinding.infoBlock.background = resources?.getDrawable(R.drawable.patient_info_background_green,null)
            2 -> visitInfoLayoutBinding.infoBlock.background = resources?.getDrawable(R.drawable.patient_info_background_blue,null)
        }
        return VisitInfoHolder(visitInfoLayoutBinding)
    }

    override fun getItemCount(): Int {
        return patientsToday.size
    }

    inner class VisitInfoHolder internal constructor(private val visitInfoLayoutBinding: VisitInfoLayoutBinding) : RecyclerView.ViewHolder(visitInfoLayoutBinding.root) {
        fun bind(patientVisitInfo: PatientVisitInfo) {
            val patient = patientVisitInfo.patient
            visitInfoLayoutBinding.patientId.text = patient.patientId
            visitInfoLayoutBinding.patientName.text = patient.firstName + " " + patient.lastName
            visitInfoLayoutBinding.patientGender.text = patient.gender.value + ", "
            visitInfoLayoutBinding.patientAge.text = getAgeText(DateTimeUtils.getTimestamp(patient.dateOfBirth))
            visitInfoLayoutBinding.patientAddress.text = patient.town + ", Tal." + patient.taluka
            visitInfoLayoutBinding.visitTime.text = getDisplayTime(DateTimeUtils.getTimestamp(patientVisitInfo.visitTime))
            visitInfoLayoutBinding.infoBlock.setOnClickListener{
                recyclerOnItemViewClickListener.onItemClicked(patient)
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
}