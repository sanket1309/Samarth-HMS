package com.samarthhms.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.samarthhms.databinding.SearchItemLayoutBinding
import com.samarthhms.models.MedicineTemplate

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