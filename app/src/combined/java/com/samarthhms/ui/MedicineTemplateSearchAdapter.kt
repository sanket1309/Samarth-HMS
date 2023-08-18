package com.samarthhms.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.samarthhms.databinding.SearchItemLayoutBinding
import com.samarthhms.models.MedicineTemplate
import com.samarthhms.models.RecyclerViewAdapter

class MedicineTemplateSearchAdapter internal
constructor(var recyclerOnItemViewClickListener: RecyclerOnItemViewClickListener,
            var templatesActual: List<MedicineTemplate> = listOf(),
            var requester: String,
            var templates: List<MedicineTemplate> = ArrayList(templatesActual),)
    : RecyclerViewAdapter<MedicineTemplateSearchAdapter.MedicineTemplateHolder, MedicineTemplate>(templates.toMutableList()), Filterable {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicineTemplateSearchAdapter.MedicineTemplateHolder {
        val searchItemLayoutBinding = SearchItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MedicineTemplateHolder(searchItemLayoutBinding)
    }

    inner class MedicineTemplateHolder internal constructor(private val searchItemLayoutBinding: SearchItemLayoutBinding)
        : RecyclerViewAdapter<MedicineTemplateSearchAdapter.MedicineTemplateHolder, MedicineTemplate>.ViewHolder(searchItemLayoutBinding.root) {
        override fun bind(medicineTemplate: MedicineTemplate) {
            searchItemLayoutBinding.itemValue.text = medicineTemplate.templateData
            searchItemLayoutBinding.searchItem.setOnClickListener{
                recyclerOnItemViewClickListener.onItemClicked(medicineTemplate, requester)
            }
        }
    }

    override fun getFilter(): Filter {
        return object: Filter(){
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filteredList = ArrayList<MedicineTemplate>()
                if(constraint == null || constraint.isEmpty()){
                    filteredList.addAll(data)
                } else{
                    val filterKey = constraint.toString().lowercase().trim()
                    data.forEach{
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
                updateData(results?.values as List<MedicineTemplate>)
            }

        }
    }
}