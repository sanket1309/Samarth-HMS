package com.samarthhms.ui

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.DrawableContainer
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.StateListDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Timestamp
import com.samarthhms.R
import com.samarthhms.databinding.MedicineTemplateLayoutBinding
import com.samarthhms.databinding.PatientHistoryTemplateLayoutBinding
import com.samarthhms.databinding.PatientInfoLayoutBinding
import com.samarthhms.databinding.VisitInfoLayoutBinding
import com.samarthhms.models.MedicineTemplate
import com.samarthhms.models.Patient
import com.samarthhms.models.PatientHistoryTemplate
import com.samarthhms.models.PatientVisitInfo
import com.samarthhms.utils.DateTimeUtils
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Period

class PatientHistoryTemplateAdapter internal constructor(var context: Context, var recyclerOnItemViewEditClickListener: RecyclerOnItemViewEditClickListener, var templates: List<PatientHistoryTemplate>) : RecyclerView.Adapter<PatientHistoryTemplateAdapter.PatientHistoryTemplateHolder>() {
    override fun onBindViewHolder(patientHistoryTemplateHolder: PatientHistoryTemplateHolder, position: Int) {
        patientHistoryTemplateHolder.bind(templates[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PatientHistoryTemplateAdapter.PatientHistoryTemplateHolder {
        val patientHistoryTemplateLayoutBinding = PatientHistoryTemplateLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PatientHistoryTemplateHolder(patientHistoryTemplateLayoutBinding)
    }

    override fun getItemCount(): Int {
        return templates.size
    }

    inner class PatientHistoryTemplateHolder internal constructor(private val patientHistoryTemplateLayoutBinding: PatientHistoryTemplateLayoutBinding) : RecyclerView.ViewHolder(patientHistoryTemplateLayoutBinding.root) {
        fun bind(patientHistoryTemplate: PatientHistoryTemplate) {
            onChangeClickable(false)
            patientHistoryTemplateLayoutBinding.template.setText(patientHistoryTemplate.templateData)
            patientHistoryTemplateLayoutBinding.templateName.setText(patientHistoryTemplate.templateName)
            patientHistoryTemplateLayoutBinding.template.setTextColor(context.resources.getColor(R.color.add_visit_edittext_text_color, null))
            if(patientHistoryTemplate.templateData == "ADD_NEW_TEMPLATE_DEFAULT"){
                onEditClicked()
                patientHistoryTemplateLayoutBinding.template.setText("")
            }
            patientHistoryTemplateLayoutBinding.editButton.setOnClickListener{
                onEditClicked()
                recyclerOnItemViewEditClickListener.onEditClicked(patientHistoryTemplate)
            }
            patientHistoryTemplateLayoutBinding.saveButton.setOnClickListener{
                val templateName = patientHistoryTemplateLayoutBinding.templateName.text.toString()
                if(templateName.isBlank()){
                    recyclerOnItemViewEditClickListener.onInvalidData("Empty Template Name")
                    return@setOnClickListener
                }

                val templateData = patientHistoryTemplateLayoutBinding.template.text.toString()
                if(templateData.isBlank()){
                    changeColorOfInputFields(patientHistoryTemplateLayoutBinding.template, R.color.red)
                    recyclerOnItemViewEditClickListener.onInvalidData("Empty Template")
                    return@setOnClickListener
                }

                changeColorOfInputFields(patientHistoryTemplateLayoutBinding.template, R.color.blue_theme)
                onSaveClicked()
                recyclerOnItemViewEditClickListener.onSaveClicked(PatientHistoryTemplate(patientHistoryTemplate.templateId, templateName, templateData))
            }
            patientHistoryTemplateLayoutBinding.deleteButton.setOnClickListener{
                recyclerOnItemViewEditClickListener.onDeleteClicked(patientHistoryTemplate)
            }
        }

        private fun changeColorOfInputFields(fieldInput: EditText, color: Int){
            val colorValue = ContextCompat.getColor(context, color)
            val fieldInputDrawable = fieldInput.background as StateListDrawable
            val dcs = fieldInputDrawable.constantState as DrawableContainer.DrawableContainerState
            val drawableItem = dcs.children[0] as GradientDrawable
            val pixels = R.dimen.login_edittext_background_stroke_width * context.resources.displayMetrics.density.toInt()
            drawableItem.setStroke(pixels, colorValue)
        }

        private fun onChangeClickable(isClickable: Boolean){
            patientHistoryTemplateLayoutBinding.templateName.isClickable = isClickable
            patientHistoryTemplateLayoutBinding.templateName.isCursorVisible = isClickable
            patientHistoryTemplateLayoutBinding.templateName.isFocusable = isClickable
            patientHistoryTemplateLayoutBinding.templateName.isFocusableInTouchMode = isClickable

            patientHistoryTemplateLayoutBinding.template.isClickable = isClickable
            patientHistoryTemplateLayoutBinding.template.isCursorVisible = isClickable
            patientHistoryTemplateLayoutBinding.template.isFocusable = isClickable
            patientHistoryTemplateLayoutBinding.template.isFocusableInTouchMode = isClickable
        }

        private fun onEditClicked(){
            patientHistoryTemplateLayoutBinding.editButton.visibility = View.GONE
            patientHistoryTemplateLayoutBinding.saveButton.visibility = View.VISIBLE
            onChangeClickable(true)
            patientHistoryTemplateLayoutBinding.template.background = context.resources.getDrawable(R.drawable.login_edittext_background, null)
            patientHistoryTemplateLayoutBinding.template.setTextColor(context.resources.getColor(R.color.black, null))
        }

        private fun onSaveClicked(){
            patientHistoryTemplateLayoutBinding.saveButton.visibility = View.GONE
            patientHistoryTemplateLayoutBinding.editButton.visibility = View.VISIBLE
            onChangeClickable(false)
            patientHistoryTemplateLayoutBinding.template.background = context.resources.getDrawable(R.drawable.add_visit_edittext_background, null)
            patientHistoryTemplateLayoutBinding.template.setTextColor(context.resources.getColor(R.color.add_visit_edittext_text_color, null))
        }
    }
}