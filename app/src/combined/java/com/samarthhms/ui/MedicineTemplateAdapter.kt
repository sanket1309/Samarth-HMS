package com.samarthhms.ui

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.DrawableContainer
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.StateListDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.samarthhms.R
import com.samarthhms.constants.Colors
import com.samarthhms.constants.Constants
import com.samarthhms.databinding.MedicineTemplateLayoutBinding
import com.samarthhms.models.MedicineTemplate
import com.samarthhms.models.RecyclerViewAdapter
import com.samarthhms.utils.EditableUtils
import com.samarthhms.utils.InputFieldColorUtils
import com.samarthhms.utils.MedicineTemplateDataExtractorUtils


class MedicineTemplateAdapter internal constructor(var context: Context, var recyclerOnItemViewEditClickListener: RecyclerOnItemViewEditClickListener, var templates: List<MedicineTemplate> = listOf())
    : RecyclerViewAdapter<MedicineTemplateAdapter.MedicineTemplateHolder,MedicineTemplate>(templates.toMutableList()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicineTemplateAdapter.MedicineTemplateHolder {
        val medicineTemplateLayoutBinding = MedicineTemplateLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MedicineTemplateHolder(medicineTemplateLayoutBinding)
    }

    inner class MedicineTemplateHolder internal constructor(private val medicineTemplateLayoutBinding: MedicineTemplateLayoutBinding)
        : RecyclerViewAdapter<MedicineTemplateAdapter.MedicineTemplateHolder,MedicineTemplate>.ViewHolder(medicineTemplateLayoutBinding.root) {

        override fun bind(medicineTemplate: MedicineTemplate) {
            EditableUtils.updateEditability(false,medicineTemplateLayoutBinding.template)
            medicineTemplateLayoutBinding.template.setText(medicineTemplate.templateData)
            medicineTemplateLayoutBinding.template.setTextColor(context.resources.getColor(R.color.add_visit_edittext_text_color, null))
            if(medicineTemplate.templateData == Constants.DefaultValues.TEMPLATE_DATA){
                updateEditability(true)
                medicineTemplateLayoutBinding.template.setText(Constants.EMPTY)
            }
            medicineTemplateLayoutBinding.editButton.setOnClickListener{ onEdit(medicineTemplate) }
            medicineTemplateLayoutBinding.saveButton.setOnClickListener{ onSave(medicineTemplate) }
            medicineTemplateLayoutBinding.deleteButton.setOnClickListener{ onDelete(medicineTemplate) }
        }

        private fun onSave(medicineTemplate: MedicineTemplate){
            val templateData = MedicineTemplateDataExtractorUtils.extractData(medicineTemplateLayoutBinding)
            if(templateData.isBlank()){
                InputFieldColorUtils.changeColorOfInputFields(medicineTemplateLayoutBinding.template, Colors.Validation.INVALID, context)
                recyclerOnItemViewEditClickListener.onInvalidData("Empty Template")
                return
            }
            InputFieldColorUtils.changeColorOfInputFields(medicineTemplateLayoutBinding.template, Colors.Validation.VALID,context)
            updateEditability(true)
            recyclerOnItemViewEditClickListener.onSaveClicked(MedicineTemplate(medicineTemplate.templateId, templateData))
        }

        private fun onEdit(medicineTemplate: MedicineTemplate){
            updateEditability(false)
            recyclerOnItemViewEditClickListener.onEditClicked(medicineTemplate)
        }

        private fun onDelete(medicineTemplate: MedicineTemplate){
            recyclerOnItemViewEditClickListener.onDeleteClicked(medicineTemplate)
        }

        @SuppressLint("UseCompatLoadingForDrawables")
        private fun updateEditability(isEditable: Boolean){
            EditableUtils.updateEditabilityForMedicineTemplate(isEditable,medicineTemplateLayoutBinding, context)
        }
    }
}