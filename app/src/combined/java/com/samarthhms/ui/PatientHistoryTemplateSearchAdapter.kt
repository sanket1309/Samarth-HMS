package com.samarthhms.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import androidx.room.util.StringUtil
import com.samarthhms.databinding.SearchItemLayoutBinding
import com.samarthhms.models.PatientHistoryTemplate
import com.samarthhms.models.RecyclerViewAdapter
import com.samarthhms.utils.StringUtils
import com.samarthhms.utils.UiDataDisplayUtils

class PatientHistoryTemplateSearchAdapter internal
constructor(var recyclerOnItemViewClickListener: RecyclerOnItemViewClickListener,
            private var templatesActual: List<PatientHistoryTemplate> = listOf(),
            private var templates: List<PatientHistoryTemplate> = ArrayList(templatesActual))
    : RecyclerViewAdapter<PatientHistoryTemplateSearchAdapter.PatientHistoryTemplateHolder, PatientHistoryTemplate>(templates.toMutableList()), Filterable {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PatientHistoryTemplateSearchAdapter.PatientHistoryTemplateHolder {
        val searchItemLayoutBinding = SearchItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PatientHistoryTemplateHolder(searchItemLayoutBinding)
    }

    inner class PatientHistoryTemplateHolder internal constructor(val searchItemLayoutBinding: SearchItemLayoutBinding)
        : RecyclerViewAdapter<PatientHistoryTemplateSearchAdapter.PatientHistoryTemplateHolder, PatientHistoryTemplate>.ViewHolder(searchItemLayoutBinding.root) {

        override fun bind(patientHistoryTemplate: PatientHistoryTemplate) {
            UiDataDisplayUtils.displaySearchItem(searchItemLayoutBinding.root, patientHistoryTemplate.templateName)
            searchItemLayoutBinding.searchItem.setOnClickListener{ onSearchItem(patientHistoryTemplate) }
        }

        private fun onSearchItem(patientHistoryTemplate: PatientHistoryTemplate){
            recyclerOnItemViewClickListener.onItemClicked(patientHistoryTemplate)
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
                    templatesActual.forEach{ it->
                        if(it.templateName.lowercase().trim().contains(filterKey)){
                            filteredList.add(it)
                        }
                    }
                }
                val filteredResults = FilterResults()
                filteredResults.values = filteredList
                return filteredResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                updateData(templates)
            }

        }
    }
}