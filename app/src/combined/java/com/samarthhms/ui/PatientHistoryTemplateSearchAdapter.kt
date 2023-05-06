package com.samarthhms.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.samarthhms.databinding.SearchItemLayoutBinding
import com.samarthhms.models.PatientHistoryTemplate

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