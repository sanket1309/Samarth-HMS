package com.samarthhms.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.samarthhms.databinding.ListItemLayoutBinding
import com.samarthhms.models.MedicineTemplate

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