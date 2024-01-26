package com.samarthhms.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.samarthhms.databinding.SearchItemLayoutBinding
import com.samarthhms.models.PatientHistoryTemplate

class PatientHistoryTemplateSearchAdapter internal constructor(var recyclerOnItemViewClickListener: RecyclerOnItemViewClickListener, var templates: List<PatientHistoryTemplate>, var templatesActual: List<PatientHistoryTemplate> = ArrayList(templates)) : RecyclerView.Adapter<PatientHistoryTemplateSearchAdapter.PatientHistoryTemplateHolder>(),Filterable {
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
                recyclerOnItemViewClickListener.onItemClicked(
                    patientHistoryTemplate,
                    isLongPress = false
                )
            }
        }
    }

    override fun getFilter(): Filter {
        return object: Filter(){
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filteredList = ArrayList<PatientHistoryTemplate>()
                if(constraint == null || constraint.isEmpty()){
                    filteredList.addAll(templatesActual)
                } else{
                    val filterKey = constraint.toString().lowercase().trim()
                    templatesActual.forEach{
                        if(it.templateData.lowercase().trim().contains(filterKey)){
                            filteredList.add(it)
                        }
                    }
                }
                val filteredResults = FilterResults()
                filteredResults.values = filteredList
                return filteredResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                templates = results?.values as List<PatientHistoryTemplate>
                notifyDataSetChanged()
            }

        }
    }
}