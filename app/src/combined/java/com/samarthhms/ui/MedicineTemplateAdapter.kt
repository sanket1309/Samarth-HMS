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
import com.samarthhms.databinding.PatientInfoLayoutBinding
import com.samarthhms.databinding.VisitInfoLayoutBinding
import com.samarthhms.models.MedicineTemplate
import com.samarthhms.models.Patient
import com.samarthhms.models.PatientVisitInfo
import com.samarthhms.utils.DateTimeUtils
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Period

class MedicineTemplateAdapter internal constructor(var context: Context, var recyclerOnItemViewEditClickListener: RecyclerOnItemViewEditClickListener, var templates: List<MedicineTemplate>) : RecyclerView.Adapter<MedicineTemplateAdapter.MedicineTemplateHolder>() {
    override fun onBindViewHolder(medicineTemplateHolder: MedicineTemplateAdapter.MedicineTemplateHolder, position: Int) {
        medicineTemplateHolder.bind(templates[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicineTemplateAdapter.MedicineTemplateHolder {
        val medicineTemplateLayoutBinding = MedicineTemplateLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MedicineTemplateHolder(medicineTemplateLayoutBinding)
    }

    override fun getItemCount(): Int {
        return templates.size
    }

    fun addEmptyItemView(){
    }

    inner class MedicineTemplateHolder internal constructor(private val medicineTemplateLayoutBinding: MedicineTemplateLayoutBinding) : RecyclerView.ViewHolder(medicineTemplateLayoutBinding.root) {
        fun bind(medicineTemplate: MedicineTemplate) {
            onChangeClickable(false)
            medicineTemplateLayoutBinding.template.setText(medicineTemplate.templateData)
            medicineTemplateLayoutBinding.template.setTextColor(context.resources.getColor(R.color.add_visit_edittext_text_color, null))
            if(medicineTemplate.templateData == "ADD_NEW_TEMPLATE_DEFAULT"){
                onEditClicked()
                medicineTemplateLayoutBinding.template.setText("")
            }
            medicineTemplateLayoutBinding.editButton.setOnClickListener{
                onEditClicked()
                recyclerOnItemViewEditClickListener.onEditClicked(medicineTemplate)
            }
            medicineTemplateLayoutBinding.saveButton.setOnClickListener{
                val templateData = medicineTemplateLayoutBinding.template.text.toString()
                if(templateData.isBlank()){
                    changeColorOfInputFields(medicineTemplateLayoutBinding.template, R.color.red)
                    recyclerOnItemViewEditClickListener.onInvalidData("Empty Template")
                    return@setOnClickListener
                }
                changeColorOfInputFields(medicineTemplateLayoutBinding.template, R.color.blue_theme)
                onSaveClicked()
                recyclerOnItemViewEditClickListener.onSaveClicked(MedicineTemplate(medicineTemplate.templateId, templateData))
            }
            medicineTemplateLayoutBinding.deleteButton.setOnClickListener{
                recyclerOnItemViewEditClickListener.onDeleteClicked(medicineTemplate)
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
            medicineTemplateLayoutBinding.template.isClickable = isClickable
            medicineTemplateLayoutBinding.template.isCursorVisible = isClickable
            medicineTemplateLayoutBinding.template.isFocusable = isClickable
            medicineTemplateLayoutBinding.template.isFocusableInTouchMode = isClickable
        }

        private fun onEditClicked(){
            medicineTemplateLayoutBinding.editButton.visibility = View.GONE
            medicineTemplateLayoutBinding.saveButton.visibility = View.VISIBLE
            onChangeClickable(true)
            medicineTemplateLayoutBinding.template.background = context.resources.getDrawable(R.drawable.login_edittext_background, null)
            medicineTemplateLayoutBinding.template.setTextColor(context.resources.getColor(R.color.black, null))
        }

        private fun onSaveClicked(){
            medicineTemplateLayoutBinding.saveButton.visibility = View.GONE
            medicineTemplateLayoutBinding.editButton.visibility = View.VISIBLE
            onChangeClickable(false)
            medicineTemplateLayoutBinding.template.background = context.resources.getDrawable(R.drawable.add_visit_edittext_background, null)
            medicineTemplateLayoutBinding.template.setTextColor(context.resources.getColor(R.color.add_visit_edittext_text_color, null))
        }
    }
}