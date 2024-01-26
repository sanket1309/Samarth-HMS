package com.samarthhms.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.samarthhms.databinding.SearchItemLayoutBinding
import com.samarthhms.models.MedicineTemplate

class MedicineTemplateSearchAdapter internal constructor(var recyclerOnItemViewClickListener: RecyclerOnItemViewClickListener, var templates: List<MedicineTemplate>, var requester: String,var templatesActual: List<MedicineTemplate> = ArrayList(templates)) : RecyclerView.Adapter<MedicineTemplateSearchAdapter.MedicineTemplateHolder>(), Filterable {
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
                recyclerOnItemViewClickListener.onItemClicked(medicineTemplate, requester, false)
            }
        }
    }

    override fun getFilter(): Filter {
        return object: Filter(){
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filteredList = ArrayList<MedicineTemplate>()
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
                templates = results?.values as List<MedicineTemplate>
                notifyDataSetChanged()
            }

        }
    }
}