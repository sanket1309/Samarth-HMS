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
import com.samarthhms.databinding.PatientHistoryTemplateLayoutBinding
import com.samarthhms.models.PatientHistoryTemplate
import com.samarthhms.models.RecyclerViewAdapter
import com.samarthhms.utils.EditableUtils
import com.samarthhms.utils.InputFieldColorUtils
import com.samarthhms.utils.UiDataDisplayUtils

class PatientHistoryTemplateAdapter internal constructor(var context: Context, var recyclerOnItemViewEditClickListener: RecyclerOnItemViewEditClickListener, var templates: List<PatientHistoryTemplate> = listOf())
    : RecyclerViewAdapter<PatientHistoryTemplateAdapter.PatientHistoryTemplateHolder,PatientHistoryTemplate>(templates.toMutableList()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PatientHistoryTemplateAdapter.PatientHistoryTemplateHolder {
        val patientHistoryTemplateLayoutBinding = PatientHistoryTemplateLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PatientHistoryTemplateHolder(patientHistoryTemplateLayoutBinding)
    }

    inner class PatientHistoryTemplateHolder internal constructor(private val patientHistoryTemplateLayoutBinding: PatientHistoryTemplateLayoutBinding)
        : RecyclerViewAdapter<PatientHistoryTemplateAdapter.PatientHistoryTemplateHolder,PatientHistoryTemplate>.ViewHolder(patientHistoryTemplateLayoutBinding.root) {

        override fun bind(patientHistoryTemplate: PatientHistoryTemplate) {
            onChangeClickable(false)
            UiDataDisplayUtils.displayPatientHistoryTemplate(patientHistoryTemplateLayoutBinding.root, patientHistoryTemplate)
            EditableUtils.setTextColor(patientHistoryTemplateLayoutBinding.template, Colors.TextColor.UNEDITABLE_TEXT,context)
            if(patientHistoryTemplate.templateData == Constants.DefaultValues.TEMPLATE_DATA){
                updateEditability(true)
                patientHistoryTemplateLayoutBinding.template.setText(Constants.EMPTY)
            }
            patientHistoryTemplateLayoutBinding.editButton.setOnClickListener{ onEdit(patientHistoryTemplate) }
            patientHistoryTemplateLayoutBinding.saveButton.setOnClickListener{ onSave(patientHistoryTemplate) }
            patientHistoryTemplateLayoutBinding.deleteButton.setOnClickListener{ onDelete(patientHistoryTemplate) }
        }

        private fun onSave(patientHistoryTemplate: PatientHistoryTemplate){
            val templateName = patientHistoryTemplateLayoutBinding.templateName.text.toString()
            if(templateName.isBlank()){
                recyclerOnItemViewEditClickListener.onInvalidData(Constants.Messages.INVALID_TEMPLATE_NAME)
                return
            }

            val templateData = patientHistoryTemplateLayoutBinding.template.text.toString()
            if(templateData.isBlank()){
                InputFieldColorUtils.changeColorOfInputFields(patientHistoryTemplateLayoutBinding.template, Colors.Validation.INVALID,context)
                recyclerOnItemViewEditClickListener.onInvalidData(Constants.Messages.INVALID_TEMPLATE)
                return
            }

            InputFieldColorUtils.changeColorOfInputFields(patientHistoryTemplateLayoutBinding.template, Colors.Validation.VALID,context)
            updateEditability(false)
            recyclerOnItemViewEditClickListener.onSaveClicked(PatientHistoryTemplate(patientHistoryTemplate.templateId, templateName, templateData))
        }

        private fun onEdit(patientHistoryTemplate: PatientHistoryTemplate){
            updateEditability(true)
            recyclerOnItemViewEditClickListener.onEditClicked(patientHistoryTemplate)
        }

        private fun onDelete(patientHistoryTemplate: PatientHistoryTemplate){
            recyclerOnItemViewEditClickListener.onDeleteClicked(patientHistoryTemplate)
        }

        private fun onChangeClickable(isClickable: Boolean){
            EditableUtils.updateEditability(isClickable, patientHistoryTemplateLayoutBinding.templateName)
            EditableUtils.updateEditability(isClickable, patientHistoryTemplateLayoutBinding.template)
        }

        private fun updateEditability(isEditable: Boolean){
            EditableUtils.updateEditabilityForTemplate(isEditable,patientHistoryTemplateLayoutBinding.root, context)
        }
    }
}