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
import com.samarthhms.databinding.PatientInfoLayoutBinding
import com.samarthhms.databinding.SearchItemLayoutBinding
import com.samarthhms.databinding.VisitInfoLayoutBinding
import com.samarthhms.models.MedicineTemplate
import com.samarthhms.models.Patient
import com.samarthhms.models.PatientVisitInfo
import com.samarthhms.utils.DateTimeUtils
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Period

class MedicineTemplateSearchAdapter internal constructor(var recyclerOnItemViewClickListener: RecyclerOnItemViewClickListener, var templates: List<MedicineTemplate>, var requester: String) : RecyclerView.Adapter<MedicineTemplateSearchAdapter.MedicineTemplateHolder>() {
    override fun onBindViewHolder(medicineTemplateHolder: MedicineTemplateSearchAdapter.MedicineTemplateHolder, position: Int) {
        medicineTemplateHolder.bind(templates[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicineTemplateSearchAdapter.MedicineTemplateHolder {
        val searchItemLayoutBinding = SearchItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MedicineTemplateHolder(searchItemLayoutBinding)
    }

    override fun getItemCount(): Int {
        return templates.size
    }

    inner class MedicineTemplateHolder internal constructor(private val searchItemLayoutBinding: SearchItemLayoutBinding) : RecyclerView.ViewHolder(searchItemLayoutBinding.root) {
        fun bind(medicineTemplate: MedicineTemplate) {
            searchItemLayoutBinding.itemValue.text = medicineTemplate.templateData
            searchItemLayoutBinding.searchItem.setOnClickListener{
                recyclerOnItemViewClickListener.onItemClicked(medicineTemplate, requester)
            }
        }
    }
}