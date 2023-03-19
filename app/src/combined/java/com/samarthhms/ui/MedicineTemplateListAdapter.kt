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
import com.samarthhms.databinding.ListItemLayoutBinding
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

class MedicineTemplateListAdapter internal constructor(var templates: MutableList<MedicineTemplate>) : RecyclerView.Adapter<MedicineTemplateListAdapter.MedicineTemplateHolder>() {
    override fun onBindViewHolder(medicineTemplateHolder: MedicineTemplateListAdapter.MedicineTemplateHolder, position: Int) {
        medicineTemplateHolder.bind(templates[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicineTemplateListAdapter.MedicineTemplateHolder {
        val listItemLayoutBinding = ListItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MedicineTemplateHolder(listItemLayoutBinding)
    }

    override fun getItemCount(): Int {
        return templates.size
    }

    inner class MedicineTemplateHolder internal constructor(val listItemLayoutBinding: ListItemLayoutBinding) : RecyclerView.ViewHolder(listItemLayoutBinding.root) {
        fun bind(medicineTemplate: MedicineTemplate) {
            listItemLayoutBinding.template.setText(if(medicineTemplate.templateData == "DEFAULT") "" else medicineTemplate.templateData)
            listItemLayoutBinding.deleteButton.setOnClickListener{
                templates.removeAt(absoluteAdapterPosition)
                notifyItemRemoved(absoluteAdapterPosition)
            }
        }
    }
}