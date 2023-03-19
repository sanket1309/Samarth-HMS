package com.samarthhms.ui

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.DrawableContainer
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.StateListDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Timestamp
import com.samarthhms.R
import com.samarthhms.databinding.MedicineTemplateLayoutBinding
import com.samarthhms.databinding.PatientHistoryTemplateLayoutBinding
import com.samarthhms.databinding.PatientInfoLayoutBinding
import com.samarthhms.databinding.SearchItemLayoutBinding
import com.samarthhms.databinding.VisitInfoLayoutBinding
import com.samarthhms.models.MedicineTemplate
import com.samarthhms.models.Patient
import com.samarthhms.models.PatientHistoryTemplate
import com.samarthhms.models.PatientVisitInfo
import com.samarthhms.utils.DateTimeUtils
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Period

class PatientHistoryTemplateSearchAdapter internal constructor(var recyclerOnItemViewClickListener: RecyclerOnItemViewClickListener, var templates: List<PatientHistoryTemplate>) : RecyclerView.Adapter<PatientHistoryTemplateSearchAdapter.PatientHistoryTemplateHolder>() {
    override fun onBindViewHolder(patientHistoryTemplateHolder: PatientHistoryTemplateHolder, position: Int) {
        patientHistoryTemplateHolder.bind(templates[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PatientHistoryTemplateSearchAdapter.PatientHistoryTemplateHolder {
        val searchItemLayoutBinding = SearchItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PatientHistoryTemplateHolder(searchItemLayoutBinding)
    }

    override fun getItemCount(): Int {
        return templates.size
    }

    inner class PatientHistoryTemplateHolder internal constructor(private val searchItemLayoutBinding: SearchItemLayoutBinding) : RecyclerView.ViewHolder(searchItemLayoutBinding.root) {
        fun bind(patientHistoryTemplate: PatientHistoryTemplate) {
            searchItemLayoutBinding.itemValue.text = patientHistoryTemplate.templateName
            searchItemLayoutBinding.searchItem.setOnClickListener{
                recyclerOnItemViewClickListener.onItemClicked(patientHistoryTemplate)
            }
        }
    }
}