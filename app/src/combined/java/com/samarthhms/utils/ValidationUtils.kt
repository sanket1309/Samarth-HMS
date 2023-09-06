package com.samarthhms.utils

import android.content.Context
import android.content.res.Resources
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.samarthhms.R
import com.samarthhms.constants.Colors
import com.samarthhms.domain.SearchPatientByNameRequest

class ValidationUtils {
    companion object{

        fun validateSearchNameRequest(searchPatientByNameRequest: SearchPatientByNameRequest, context: Context){
            if(searchPatientByNameRequest.name!!.firstName.isNullOrBlank() &&
                searchPatientByNameRequest.name!!.middleName.isNullOrBlank() &&
                searchPatientByNameRequest.name!!.lastName.isNullOrBlank() &&
                searchPatientByNameRequest.contactNumber.isNullOrBlank() &&
                searchPatientByNameRequest.town.isNullOrBlank() &&
                    searchPatientByNameRequest.dateRange == null){
                Toast.makeText(context, "All names cant be empty", Toast.LENGTH_SHORT).show()
                throw Exception()
            }
        }

        private fun validateName(rootView: View, context: Context, resources: Resources){
            try{
                validateTextField(rootView, R.id.first_name_title, R.id.first_name, Validation::validateName, context, resources, InputFormatterUtils::formatName)
//                validateTextField(rootView, R.id.middle_name_title, R.id.middle_name, Validation::validateName, context, resources, InputFormatterUtils::formatName)
                validateTextField(rootView, R.id.last_name_title, R.id.last_name, Validation::validateName, context, resources, InputFormatterUtils::formatName)
            }catch (e: Exception){
                Log.e("ValidateIndividualFormUtils","Validation failed for name",e)
                throw e
            }
        }

        private fun validateLocation(rootView: View, context: Context, resources: Resources){

            try{
                validateTextField(rootView, R.id.town_title, R.id.town, Validation::validatePlaceName, context, resources, InputFormatterUtils::formatName)
                validateTextField(rootView, R.id.taluka_title, R.id.taluka, Validation::validatePlaceName, context, resources, InputFormatterUtils::formatName)
                validateTextField(rootView, R.id.district_title, R.id.district, Validation::validatePlaceName, context, resources, InputFormatterUtils::formatName)
            }catch (e: Exception){
                Log.e("ValidateIndividualFormUtils","Validation failed for location",e)
                throw e
            }
        }

        private fun validateDateOfBirth(rootView: View, context: Context, resources: Resources){
            try{
                validateTextField(rootView, R.id.date_of_birth_title, R.id.date_of_birth, Validation::validateDate, context, resources, InputFormatterUtils::formatString)
            }catch (e: Exception){
                Log.e("ValidateIndividualFormUtils","Validation failed for date of birth",e)
                throw e
            }
        }

        private fun validateContactNumber(rootView: View, context: Context, resources: Resources){

            try{

            }catch (e: Exception){
                Log.e("ValidateIndividualFormUtils","Validation failed for contact number",e)
                throw e
            }
        }

        private fun validateAddress(rootView: View, context: Context, resources: Resources){

            try{
                validateTextField(rootView, R.id.address_title, R.id.address, Validation::validateString, context, resources, InputFormatterUtils::formatString)
            }catch (e: Exception){
                Log.e("ValidateIndividualFormUtils","Validation failed for address",e)
                throw e
            }
        }

        private fun validateAge(rootView: View, context: Context, resources: Resources){

            try{
                validateTextField(rootView, R.id.age_title, R.id.age, Validation::validateAge, context, resources, InputFormatterUtils::formatString)
            }catch (e: Exception){
                Log.e("ValidateIndividualFormUtils","Validation failed for age",e)
                throw e
            }
        }

        //DONE
        fun validateAdminDetails(rootView: View, context: Context, resources: Resources){
            try{
                validateTextField(rootView, R.id.first_name_title, R.id.first_name, Validation::validateName, context, resources, InputFormatterUtils::formatName)
                validateTextField(rootView, R.id.middle_name_title, R.id.middle_name, Validation::validateName, context, resources, InputFormatterUtils::formatName)
                validateTextField(rootView, R.id.last_name_title, R.id.last_name, Validation::validateName, context, resources, InputFormatterUtils::formatName)
                validateTextField(rootView, R.id.date_of_birth_title, R.id.date_of_birth, Validation::validateDate, context, resources, InputFormatterUtils::formatString)
                validateTextField(rootView, R.id.contact_number_title, R.id.contact_number, Validation::validateContactNumber, context, resources, InputFormatterUtils::formatContactNumberFromEdittext)
                validateTextField(rootView, R.id.address_title, R.id.address, Validation::validateString, context, resources, InputFormatterUtils::formatString)
                validateTextField(rootView, R.id.username_title, R.id.username, Validation::validateUserName, context, resources, InputFormatterUtils::formatString)
                validateTextField(rootView, R.id.password_title, R.id.password, Validation::validatePassword, context, resources, InputFormatterUtils::formatString)
            }catch (e: Exception){
                Log.e("ValidateIndividualFormUtils","Validation failed for admin details",e)
                throw e
            }
        }

        //DONE
        fun validateStaffDetails(rootView: View, context: Context, resources: Resources){
            try{
                validateTextField(rootView, R.id.first_name_title, R.id.first_name, Validation::validateName, context, resources, InputFormatterUtils::formatName)
                validateTextField(rootView, R.id.middle_name_title, R.id.middle_name, Validation::validateName, context, resources, InputFormatterUtils::formatName)
                validateTextField(rootView, R.id.last_name_title, R.id.last_name, Validation::validateName, context, resources, InputFormatterUtils::formatName)
                validateTextField(rootView, R.id.date_of_birth_title, R.id.date_of_birth, Validation::validateDate, context, resources, InputFormatterUtils::formatString)
                validateTextField(rootView, R.id.contact_number_title, R.id.contact_number, Validation::validateContactNumber, context, resources, InputFormatterUtils::formatContactNumberFromEdittext)
                validateTextField(rootView, R.id.address_title, R.id.address, Validation::validateString, context, resources, InputFormatterUtils::formatString)
                validateTextField(rootView, R.id.username_title, R.id.username, Validation::validateUserName, context, resources, InputFormatterUtils::formatString)
                validateTextField(rootView, R.id.password_title, R.id.password, Validation::validatePassword, context, resources, InputFormatterUtils::formatString)
            }catch (e: Exception){
                Log.e("ValidateIndividualFormUtils","Validation failed for staff details",e)
                throw e
            }
        }

        //DONE
        fun validatePatient(rootView: View, context: Context, resources: Resources){

            try{
                validateTextField(rootView, R.id.first_name_title, R.id.first_name, Validation::validateName, context, resources, InputFormatterUtils::formatName)
//                validateTextField(rootView, R.id.middle_name_title, R.id.middle_name, Validation::validateName, context, resources, InputFormatterUtils::formatName)
                validateTextField(rootView, R.id.last_name_title, R.id.last_name, Validation::validateName, context, resources, InputFormatterUtils::formatName)
//                validateTextField(rootView, R.id.date_of_birth_title, R.id.date_of_birth, Validation::validateDate, context, resources, InputFormatterUtils::formatString)
//                validateTextField(rootView, R.id.age_title, R.id.age, Validation::validateAge, context, resources, InputFormatterUtils::formatString)
                validateTextField(rootView, R.id.contact_number_title, R.id.contact_number, Validation::validateContactNumber, context, resources, InputFormatterUtils::formatContactNumberFromEdittext)
                validateTextField(rootView, R.id.town_title, R.id.town, Validation::validatePlaceName, context, resources, InputFormatterUtils::formatString)
                validateTextField(rootView, R.id.taluka_title, R.id.taluka, Validation::validatePlaceName, context, resources, InputFormatterUtils::formatString)
                validateTextField(rootView, R.id.district_title, R.id.district, Validation::validatePlaceName, context, resources, InputFormatterUtils::formatString)
            }catch (e: Exception){
                Log.e("ValidateIndividualFormUtils","Validation failed for patient",e)
                throw e
            }
        }

        private fun validateCredentials(rootView: View, context: Context, resources: Resources){

            try{
                validateTextField(rootView, R.id.username_title, R.id.username, Validation::validateUserName, context, resources, InputFormatterUtils::formatString)
                validateTextField(rootView, R.id.password_title, R.id.password, Validation::validatePassword, context, resources, InputFormatterUtils::formatString)
            }catch (e: Exception){
                Log.e("ValidateIndividualFormUtils","Validation failed for credentials",e)
                throw e
            }
        }

        fun validateDischargeCard(rootView: View, context: Context, resources: Resources){

            try{
                validateTextField(rootView, R.id.ipd_number_title, R.id.ipd_number, Validation::validateIpdNumber, context, resources, InputFormatterUtils::formatString)
//                validateName(rootView, context, resources)
//                validateDateOfBirth(rootView, context, resources)
//                validateAge(rootView, context, resources)
//                validateContactNumber(rootView, context, resources)
//                validateTextField(rootView, R.id.address_title, R.id.address, Validation::validateString, context, resources, InputFormatterUtils::formatString)
//                validateTextField(rootView, R.id.age_title, R.id.age, Validation::validateAge, context, resources, InputFormatterUtils::formatAge)
//                validateTextField(rootView, R.id.date_of_admission_title, R.id.date_of_admission, Validation::validateDate, context, resources, InputFormatterUtils::formatString)
//                validateTextField(rootView, R.id.date_of_discharge_title, R.id.date_of_discharge, Validation::validateDate, context, resources, InputFormatterUtils::formatString)
//                validateTextField(rootView, R.id.diagnosis_title, R.id.diagnosis, Validation::validateString, context, resources, InputFormatterUtils::formatString)
//                validateTextField(rootView, R.id.patient_history_title, R.id.patient_history, Validation::validateString, context, resources, InputFormatterUtils::formatString)
//                validateTextField(rootView, R.id.past_history_title, R.id.past_history, Validation::validateString, context, resources, InputFormatterUtils::formatString)
//                validateTextField(rootView, R.id.family_history_title, R.id.family_history, Validation::validateString, context, resources, InputFormatterUtils::formatString)
//                validateTextField(rootView, R.id.investigations_title, R.id.investigations, Validation::validateString, context, resources, InputFormatterUtils::formatString)
//                validateMedicineTemplates(getView(R.id.course_list, rootView), context, resources)
//                validateMedicineTemplates(getView(R.id.medication_list, rootView), context, resources)
//                validateMedicineTemplates(getView(R.id.advice_list, rootView), context, resources)
            }catch (e: Exception){
                Log.e("ValidateIndividualFormUtils","Validation failed for discharge card",e)
                throw e
            }
        }

        private fun validateMedicineTemplates(recyclerView: RecyclerView, context: Context, resources: Resources ){
            try{
                val medicineTemplateHolders = UiDataExtractorUtils.getMedicineTemplateHolders(recyclerView)
                for (medicineTemplateHolder in medicineTemplateHolders) {
                    val itemBinding = medicineTemplateHolder.listItemLayoutBinding
                    validateTextField(itemBinding.root, R.id.item_name_title, R.id.item_name, Validation::validateString, context,resources, InputFormatterUtils::formatString)
                }
            }catch (e: Exception){
                Log.e("ValidateIndividualFormUtils","Validation failed for medicine templates",e)
                throw e
            }
        }

        private fun<T:View> getView(viewId: Int, rootView: View):T{
            return rootView.findViewById(viewId)
        }

        fun validateBill(rootView: View, context: Context, resources: Resources){

            try{
                validateTextField(rootView, R.id.bill_number_title, R.id.bill_number, Validation::validateBillNumber, context, resources)
//                validateName(rootView, context, resources)
//                validateDateOfBirth(rootView, context, resources)
//                validateAge(rootView, context, resources)
//                validateContactNumber(rootView, context, resources)
//                validateTextField(rootView, R.id.address_title, R.id.address, Validation::validateAge, context, resources, InputFormatterUtils::formatAge)
//                validateTextField(rootView, R.id.age_title, R.id.age, Validation::validateAge, context, resources, InputFormatterUtils::formatAge)
//                validateTextField(rootView, R.id.date_of_admission_title, R.id.date_of_admission, Validation::validateDate, context, resources, InputFormatterUtils::formatString)
//                validateTextField(rootView, R.id.date_of_discharge_title, R.id.date_of_discharge, Validation::validateDate, context, resources, InputFormatterUtils::formatString)
//                validateTextField(rootView, R.id.diagnosis_title, R.id.diagnosis, Validation::validateString, context, resources, InputFormatterUtils::formatString)
//                validateMedicineTemplates(getView(R.id.treatment_charges, rootView), context, resources)
//                validateMedicineTemplates(getView(R.id.management_charges, rootView), context, resources)
//                validateTextField(rootView, R.id.other_charges_title, R.id.other_charges, Validation::validateString, context, resources, InputFormatterUtils::formatString)
            }catch (e: Exception){
                Log.e("ValidateIndividualFormUtils","Validation failed for bill",e)
                throw e
            }
        }

        fun validateBillItems(recyclerView: RecyclerView, context: Context, resources: Resources ){
            try{
                val billItemHolders = UiDataExtractorUtils.getBillItemHolders(recyclerView)
                for (billItemHolder in billItemHolders) {
                    val itemBinding = billItemHolder.billItemLayoutBinding
                    validateTextField(itemBinding.root, R.id.item_name_title, R.id.item_name, Validation::validateString, context,resources, InputFormatterUtils::formatString)
                }
            }catch (e: Exception){
                Log.e("ValidateIndividualFormUtils","Validation failed for bill item",e)
                throw e
            }
        }

        private fun validateTextField(rootView: View, titleId: Int, inputId: Int, validationFunction: (field: String)->Boolean, context: Context, resources: Resources, inputFormatter: ((field: String?)->String)? = null){
            val titleEditText = rootView.findViewById<TextView>(titleId)
            val inputEditText = rootView.findViewById<EditText>(inputId)
            var fieldValue = inputEditText.text.toString()
            if(inputFormatter != null) {
                fieldValue = inputFormatter(fieldValue)
            }
            validateTextField(fieldValue, validationFunction, titleEditText, inputEditText, context, resources)
        }


        private fun validateTextField(textField: String, validationFunction: (field: String)->Boolean, fieldTitle: TextView, field: EditText, context: Context, resources: Resources){
            if(!validationFunction(textField)){
                InputFieldColorUtils.changeColorOfInputFields(fieldTitle, field, Colors.Validation.INVALID, context, resources)
                ToastUtils.showToast(context, "Invalid "+fieldTitle.text.toString())
                throw Exception("Text validation failed")
            }else{
                InputFieldColorUtils.changeColorOfInputFields(fieldTitle, field, Colors.Validation.VALID, context, resources)
            }
        }
    }
}