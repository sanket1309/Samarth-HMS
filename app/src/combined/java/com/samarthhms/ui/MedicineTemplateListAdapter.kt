package com.samarthhms.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.samarthhms.databinding.ListItemLayoutBinding
import com.samarthhms.models.MedicineTemplate
import com.samarthhms.models.RecyclerViewAdapter
import com.samarthhms.utils.UiDataDisplayUtils

class MedicineTemplateListAdapter internal constructor(var templates: MutableList<String> = mutableListOf())
    : RecyclerViewAdapter<MedicineTemplateListAdapter.MedicineTemplateHolder, String>(templates) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicineTemplateListAdapter.MedicineTemplateHolder {
        val listItemLayoutBinding = ListItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MedicineTemplateHolder(listItemLayoutBinding)
    }

    inner class MedicineTemplateHolder internal constructor(val listItemLayoutBinding: ListItemLayoutBinding)
        : RecyclerViewAdapter<MedicineTemplateListAdapter.MedicineTemplateHolder, String>.ViewHolder(listItemLayoutBinding.root) {
        override fun bind(templateData: String) {
            UiDataDisplayUtils.displayMedicineTemplate(listItemLayoutBinding.root, templateData)
//            TODO ADD DEFAULT CHECK LIKE BELOW IN OUTPUT FORMATTER
//            listItemLayoutBinding.template.setText(if(medicineTemplate.templateData == "DEFAULT") "" else medicineTemplate.templateData)
            listItemLayoutBinding.deleteButton.setOnClickListener{ deleteItem(absoluteAdapterPosition) }
        }
    }
}