package com.samarthhms.utils

import android.content.Context
import android.content.res.Resources
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.samarthhms.R
import com.samarthhms.constants.Colors
import com.samarthhms.constants.Constants
import com.samarthhms.databinding.MedicineTemplateLayoutBinding
import com.samarthhms.databinding.PatientInfoLayoutBinding
import java.time.Duration

class EditableUtils {
    companion object{
        fun updateEditability(isEditable: Boolean, editText: EditText) {
            editText.isClickable=isEditable
            editText.isCursorVisible=isEditable
            editText.isFocusable=isEditable
            editText.isFocusableInTouchMode=isEditable
        }

        fun updateEditabilityForIndividualForm(isEditable: Boolean, rootView: View){
            val drawableId = if(!isEditable) Constants.Drawables.UNEDITABLE_EDITTEXT else Constants.Drawables.EDITABLE_EDITTEXT
            val textColorId = if(isEditable) Constants.Drawables.EDITABLE_EDITTEXT else Constants.Drawables.UNEDITABLE_EDITTEXT

            updateEditabilityForEditText(isEditable, R.id.first_name, rootView, drawableId,textColorId)
            updateEditabilityForEditText(isEditable, R.id.middle_name, rootView, drawableId,textColorId)
            updateEditabilityForEditText(isEditable, R.id.last_name, rootView, drawableId,textColorId)
            updateEditabilityForEditText(isEditable, R.id.date_of_birth, rootView, drawableId,textColorId)
            updateEditabilityForEditText(isEditable, R.id.contact_number, rootView, drawableId,textColorId)
            updateEditabilityForEditText(isEditable, R.id.address, rootView, drawableId,textColorId)
            updateEditabilityForEditText(isEditable, R.id.username, rootView, drawableId,textColorId)
            updateEditabilityForEditText(isEditable, R.id.password, rootView, drawableId,textColorId)
            updateEditability(isEditable, R.id.gender_male_radio_group_button,rootView)
            updateEditability(isEditable, R.id.gender_female_radio_group_button,rootView)
            updateVisibility(!isEditable, R.id.show_password_button,rootView)
        }

        fun updateEditabilityForEditText(isEditable: Boolean, rootView: View){
            updateVisibility(!isEditable, R.id.edit_admin_button,rootView,false)
            updateVisibility(isEditable, R.id.save_admin_button,rootView,false)
            updateEditabilityForIndividualForm(isEditable, rootView)
            ShowPasswordUtils.onPasswordVisibilityToggle(
                getView(R.id.show_password_button, rootView),
                getView(R.id.password, rootView),
                true)
        }

        fun updateEditabilityForAdminDetails(isEditable: Boolean, rootView: View){
            updateVisibility(!isEditable, R.id.edit_admin_button,rootView,false)
            updateVisibility(isEditable, R.id.save_admin_button,rootView,false)
            updateEditabilityForIndividualForm(isEditable, rootView)
            ShowPasswordUtils.onPasswordVisibilityToggle(
                getView(R.id.show_password_button, rootView),
                getView(R.id.password, rootView),
                true)
        }

        fun updateEditabilityForTemplate(isEditable: Boolean,rootView: View, context: Context){
            updateVisibility(!isEditable, R.id.edit_button,rootView ,false)
            updateVisibility(isEditable, R.id.save_button,rootView ,false)
            updateEditability(isEditable, R.id.template,rootView)
            val backgroundResourceId = if(isEditable) Constants.Drawables.EDITABLE_EDITTEXT else Constants.Drawables.UNEDITABLE_EDITTEXT
            setBackground(getView(R.id.template,rootView), backgroundResourceId)
            val textColorId = if(isEditable) Colors.TextColor.EDITABLE_TEXT else Colors.TextColor.UNEDITABLE_TEXT
            setTextColor(getView(R.id.template,rootView), getColorResourceId(textColorId, context))
        }

        fun updateEditabilityForMedicineTemplate(isEditable: Boolean,medicineTemplateLayoutBinding: MedicineTemplateLayoutBinding, context: Context){
            updateVisibility(!isEditable, medicineTemplateLayoutBinding.editButton,true)
            updateVisibility(isEditable, medicineTemplateLayoutBinding.saveButton,true)
            updateEditability(isEditable,medicineTemplateLayoutBinding.template)
            val backgroundResourceId = if(isEditable) Constants.Drawables.EDITABLE_EDITTEXT else Constants.Drawables.UNEDITABLE_EDITTEXT
            setBackground(medicineTemplateLayoutBinding.template, backgroundResourceId)
            val textColorId = if(isEditable) Colors.TextColor.EDITABLE_TEXT else Colors.TextColor.UNEDITABLE_TEXT
            setTextColor(medicineTemplateLayoutBinding.template, textColorId)
        }

        fun<T:View> getView(viewId: Int, rootView: View):T{
            return rootView.findViewById(viewId)
        }

        fun updateVisibility(isVisible: Boolean, view: View, isGone: Boolean = true){
            view.visibility = if(isVisible) View.VISIBLE else { if(isGone) View.GONE else View.INVISIBLE }
        }

        private fun updateVisibility(isVisible: Boolean, viewId: Int, rootView: View, isGone: Boolean = true){
            val view = rootView.findViewById(viewId) as View
            view.visibility = if(isVisible) View.VISIBLE else { if(isGone) View.GONE else View.INVISIBLE }
        }

        private fun updateEditability(isEditable: Boolean, viewId: Int, rootView: View){
            val view = rootView.findViewById(viewId) as View
            view.isEnabled = isEditable
        }

        private fun setBackground(view:View,resourceId: Int){
            view.setBackgroundResource(resourceId)
        }

        private fun getColorResourceId(colorId: Int,context: Context, theme:Resources.Theme? = null):Int{
            return context.resources.getColor(colorId, theme)
        }

        public fun<T:TextView> setTextColor(textView:T,colorId: Int, context: Context){
            val resourceId = getColorResourceId(colorId, context)
            textView.setTextColor(resourceId)
        }

        private fun<T:TextView> setTextColor(textView:T,resourceId: Int){
            textView.setTextColor(resourceId)
        }

        private fun updateEditabilityForEditText(isEditable: Boolean, editTextId: Int, rootView: View,backgroundResourceId: Int, textColorResourceId: Int){
            val editText = rootView.findViewById<EditText>(editTextId)
            updateEditability(isEditable, editText)
            setBackground(editText, backgroundResourceId)
            setTextColor(editText, textColorResourceId)
        }
    }
}