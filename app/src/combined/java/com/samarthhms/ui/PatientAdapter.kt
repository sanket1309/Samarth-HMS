package com.samarthhms.ui

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Timestamp
import com.samarthhms.R
import com.samarthhms.constants.Constants
import com.samarthhms.databinding.PatientInfoLayoutBinding
import com.samarthhms.models.Patient
import com.samarthhms.models.RecyclerViewAdapter
import com.samarthhms.utils.DateTimeUtils
import com.samarthhms.utils.UiDataDisplayUtils
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Period

class PatientAdapter internal constructor(var context: Context?, var recyclerOnItemViewClickListener : RecyclerOnItemViewClickListener, var patients: List<Patient> = listOf())
    : RecyclerViewAdapter<PatientAdapter.PatientHolder, Patient>(patients.toMutableList()) {

    override fun getItemViewType(position: Int): Int {
        return (itemCount-position-1) % Constants.Drawables.LIST_ITEM_BACKGROUNDS.size
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PatientAdapter.PatientHolder {
        val patientInfoLayoutBinding = PatientInfoLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val resources = context?.resources
        patientInfoLayoutBinding.infoBlock.background = resources?.getDrawable(Constants.Drawables.LIST_ITEM_BACKGROUNDS[viewType],null)
        return PatientHolder(patientInfoLayoutBinding)
    }

    inner class PatientHolder internal constructor(private val patientInfoLayoutBinding: PatientInfoLayoutBinding)
        : RecyclerViewAdapter<PatientAdapter.PatientHolder, Patient>.ViewHolder(patientInfoLayoutBinding.root) {

        override fun bind(patient: Patient) {
            UiDataDisplayUtils.displayPatientItem(patientInfoLayoutBinding.root, patient)
            patientInfoLayoutBinding.infoBlock.setOnClickListener{
                recyclerOnItemViewClickListener.onItemClicked(patient)
            }
        }
    }
}