package com.samarthhms.utils

import android.content.Context
import android.content.res.Resources
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.samarthhms.R
import com.samarthhms.constants.*
import com.samarthhms.databinding.BillItemLayoutBinding
import com.samarthhms.databinding.DischargeCardInfoLayoutBinding
import com.samarthhms.databinding.FragmentEditDischargeCardBinding
import com.samarthhms.databinding.FragmentGenerateBillBinding
import com.samarthhms.databinding.MedicineTemplateLayoutBinding
import com.samarthhms.domain.SearchPatientByNameRequest
import com.samarthhms.models.*
import com.samarthhms.ui.BillAdapter
import com.samarthhms.ui.MedicineTemplateListAdapter
import java.math.BigInteger
import java.time.LocalDateTime
import java.util.*

class UiDataExtractorUtils {
    companion object{

        fun getSearchPatient(rootView: View): SearchPatientByNameRequest{

            val request = SearchPatientByNameRequest(Name())
            request.name!!.firstName = getText(rootView,R.id.first_name, InputFormatterUtils::formatName, Validation::validateName, Constants.EMPTY)!!
            request.name!!.middleName = getText(rootView,R.id.middle_name, InputFormatterUtils::formatName, Validation::validateName, Constants.EMPTY)!!
            request.name!!.lastName = getText(rootView,R.id.last_name, InputFormatterUtils::formatName, Validation::validateName, Constants.EMPTY)!!
            request.contactNumber = getText(rootView,R.id.contact_number, InputFormatterUtils::formatString, Validation::validateContactNumber)
            request.town = getText(rootView,R.id.town, InputFormatterUtils::formatString, Validation::validatePlaceName)

            val fromDateValue = getTextFromTextView(rootView,R.id.from_date, InputFormatterUtils::formatString, Validation::validateString)!!
            val fromDateRangeValue = DateRangeValue.getByValue(fromDateValue)
            if(Objects.nonNull(fromDateRangeValue) && fromDateRangeValue != DateRangeValue.CUSTOM){
                request.dateRange = DateTimeUtils.getDateRange(fromDateRangeValue!!)
            }else{
                val dateRange = DateRange()
                dateRange.startDate = getDate(rootView,R.id.from_date, InputFormatterUtils::formatString, Validation::validateDate)
                dateRange.endDate = getDate(rootView,R.id.to_date, InputFormatterUtils::formatString, Validation::validateDate)
                request.dateRange = dateRange
            }
            return request
        }
        fun getBillItemWithoutItemName(billItemLayoutBinding: BillItemLayoutBinding): BillItem {
            try{
                val billItem = BillItem()
                billItem.rate = getInteger(billItemLayoutBinding.rate, Validation::validateInteger, BigInteger.ZERO.toString())!!
                billItem.quantity = getInteger(billItemLayoutBinding.quantity, Validation::validateInteger, BigInteger.ZERO.toString())!!
                billItem.sum = getInteger(billItemLayoutBinding.sum, Validation::validateInteger, BigInteger.ZERO.toString())!!
                return billItem
            }catch (e: Exception){
                Log.e("BillDataExtractorUtils","Failed to get bill item without item name",e)
                throw e
            }
        }

        fun getBill(rootView: View): Bill {
            try{
                val bill= Bill()
                bill.firstName = getText(rootView,R.id.first_name, InputFormatterUtils::formatName, Validation::validateName, Constants.EMPTY)!!
                bill.middleName = getText(rootView,R.id.middle_name, InputFormatterUtils::formatName, Validation::validateName, Constants.EMPTY)!!
                bill.lastName = getText(rootView,R.id.last_name, InputFormatterUtils::formatName, Validation::validateName, Constants.EMPTY)!!
                bill.gender = getGender(rootView,R.id.gender_male_radio_group_button)
                bill.contactNumber = getText(rootView,R.id.contact_number, InputFormatterUtils::formatContactNumberFromEdittext, Validation::validateDate)
                bill.dateOfBirth = getDate(rootView,R.id.date_of_birth, InputFormatterUtils::formatString, Validation::validateDate)
                bill.address = getText(rootView,R.id.address, InputFormatterUtils::formatString, Validation::validateString)!!
                bill.dateOfAdmission = getDate(rootView,R.id.date_of_admission, InputFormatterUtils::formatString, Validation::validateDate)
                bill.dateOfDischarge = getDate(rootView,R.id.date_of_discharge, InputFormatterUtils::formatString, Validation::validateDate)
                bill.diagnosis = getText(rootView,R.id.diagnosis, InputFormatterUtils::formatString, Validation::validateString)!!
                bill.managementCharges = getBillItems(getView(R.id.management_charges, rootView))
                bill.treatmentCharges = getBillItems(getView(R.id.treatment_charges, rootView))
                bill.otherCharges =getBillItem(getView(R.id.other_charges, rootView))
                return bill
            }catch (e: Exception){
                Log.e("BillDataExtractorUtils","Failed to extract bill",e)
                throw e
            }
        }

        fun getMedicineTemplate(medicineTemplateLayoutBinding: MedicineTemplateLayoutBinding): MedicineTemplate {
            try{
                val medicineTemplate = MedicineTemplate()
                medicineTemplate.templateData = getText(medicineTemplateLayoutBinding.template, InputFormatterUtils::formatString, Validation::validateString, Constants.EMPTY)!!
                return medicineTemplate
            }catch (e: Exception){
                Log.e("BillDataExtractorUtils","Failed to get medicine template",e)
                throw e
            }
        }

        private fun getMedicineTemplates(recyclerView: RecyclerView): List<MedicineTemplate>{
            val medicineTemplateHolders = getMedicineTemplateHolders(recyclerView)
            val medicineTemplates = mutableListOf<MedicineTemplate>()
            for (medicineTemplateHolder in medicineTemplateHolders) {
                val itemBinding = medicineTemplateHolder.listItemLayoutBinding
                val medicineTemplate = getMedicineTemplate(itemBinding.root)
                medicineTemplates.add(medicineTemplate)
            }
            return medicineTemplates
        }

        fun getMedicineTemplateHolders(recyclerView: RecyclerView): List<MedicineTemplateListAdapter.MedicineTemplateHolder>{
            val list = mutableListOf<MedicineTemplateListAdapter.MedicineTemplateHolder>()
            var billItemHolder : MedicineTemplateListAdapter.MedicineTemplateHolder
            for(position in 0 until recyclerView.childCount){
                billItemHolder = recyclerView.getChildViewHolder(recyclerView.getChildAt(position)) as MedicineTemplateListAdapter.MedicineTemplateHolder
                list.add(billItemHolder)
            }
            return list
        }

        private fun getBillItems(recyclerView: RecyclerView): List<BillItem>{
            val billItemHolders = getBillItemHolders(recyclerView)
            val billItems = mutableListOf<BillItem>()
            for (billItemHolder in billItemHolders) {
                val itemBinding = billItemHolder.billItemLayoutBinding
                val billItem =getBillItem(itemBinding.root)
                billItems.add(billItem)
            }
            return billItems
        }

        fun getBillItemHolders(recyclerView: RecyclerView): List<BillAdapter.BillItemHolder>{
            val list = mutableListOf<BillAdapter.BillItemHolder>()
            var billItemHolder : BillAdapter.BillItemHolder
            for(position in 0 until recyclerView.childCount){
                billItemHolder = recyclerView.getChildViewHolder(recyclerView.getChildAt(position)) as BillAdapter.BillItemHolder
                list.add(billItemHolder)
            }
            return list
        }

        private fun getName(rootView: View, name: Name) {
            try{
                name.firstName = getText(rootView,R.id.first_name, InputFormatterUtils::formatName, Validation::validateName, Constants.EMPTY)!!
                name.middleName = getText(rootView,R.id.middle_name, InputFormatterUtils::formatName, Validation::validateName, Constants.EMPTY)!!
                name.lastName = getText(rootView,R.id.last_name, InputFormatterUtils::formatName, Validation::validateName, Constants.EMPTY)!!
            }catch (e: Exception){
                Log.e("IndividualFormUtils","Validation failed for name",e)
                throw e
            }
        }

        private fun getLocation(rootView: View, location: Location) {
            try{
                location.town = getText(rootView,R.id.town, InputFormatterUtils::formatString, Validation::validatePlaceName)!!
                location.taluka = getText(rootView,R.id.taluka, InputFormatterUtils::formatString, Validation::validatePlaceName)!!
                location.district = getText(rootView,R.id.district, InputFormatterUtils::formatString, Validation::validatePlaceName)!!
            }catch (e: Exception){
                Log.e("IndividualFormUtils","Validation failed for name",e)
                throw e
            }
        }

        fun getAgeText(rootView: View): String {
            try{
                return getText(rootView,R.id.age, InputFormatterUtils::formatString, Validation::validateAge)!!
            }catch (e: Exception){
                Log.e("IndividualFormUtils","Validation failed for name",e)
                throw e
            }
        }

        //DONE
        fun getPatient(rootView: View) : Patient {
            try{
                val patient = Patient()
                patient.firstName = getText(rootView,R.id.first_name, InputFormatterUtils::formatName, Validation::validateName, Constants.EMPTY)!!
                patient.middleName = getText(rootView,R.id.middle_name, InputFormatterUtils::formatName, Validation::validateName)
                patient.lastName = getText(rootView,R.id.last_name, InputFormatterUtils::formatName, Validation::validateName, Constants.EMPTY)!!
                patient.gender = getGender(rootView,R.id.gender_male_radio_group_button)
                patient.contactNumber = getText(rootView,R.id.contact_number, InputFormatterUtils::formatContactNumberFromEdittext, Validation::validateContactNumber)
                patient.dateOfBirth = getDate(rootView,R.id.date_of_birth, InputFormatterUtils::formatString, Validation::validateDate)
                patient.ageInText = getText(rootView,R.id.age, InputFormatterUtils::formatString, Validation::validateAge)
                patient.town = getText(rootView,R.id.town, InputFormatterUtils::formatString, Validation::validatePlaceName, Constants.DefaultValues.TOWN)
                patient.taluka = getText(rootView,R.id.taluka, InputFormatterUtils::formatString, Validation::validatePlaceName, Constants.DefaultValues.TALUKA)
                patient.district = getText(rootView,R.id.district, InputFormatterUtils::formatString, Validation::validatePlaceName, Constants.DefaultValues.DISTRICT)
                return patient
            }catch (e: Exception){
                Log.e("IndividualFormUtils","Validation failed for patient",e)
                throw e
            }
        }

        //DONE
        fun getAdminDetails(rootView: View) : AdminDetails {
            try{
                val admin = Admin()
                admin.firstName = getText(rootView,R.id.first_name, InputFormatterUtils::formatName, Validation::validateName, Constants.EMPTY)!!
                admin.middleName = getText(rootView,R.id.middle_name, InputFormatterUtils::formatName, Validation::validateName)
                admin.lastName = getText(rootView,R.id.last_name, InputFormatterUtils::formatName, Validation::validateName, Constants.EMPTY)!!
                admin.gender = getGender(rootView,R.id.gender_male_radio_group_button)
                admin.contactNumber = getText(rootView,R.id.contact_number, InputFormatterUtils::formatContactNumberFromEdittext, Validation::validateContactNumber)
                admin.dateOfBirth = getDate(rootView,R.id.date_of_birth, InputFormatterUtils::formatString, Validation::validateDate)
                admin.address = getText(rootView,R.id.address, InputFormatterUtils::formatString, Validation::validateString)
                val credentials = getCredentials(rootView)
                credentials.role = Role.ADMIN
                return AdminDetails(admin = admin, adminCredentials = credentials)
            }catch (e: Exception){
                Log.e("IndividualFormUtils","Validation failed",e)
                throw e
            }
        }

        //DONE
        fun getStaffDetails(rootView: View) : StaffDetails {
            try{
                val staff = Staff()
                staff.firstName = getText(rootView,R.id.first_name, InputFormatterUtils::formatName, Validation::validateName, Constants.EMPTY)!!
                staff.middleName = getText(rootView,R.id.middle_name, InputFormatterUtils::formatName, Validation::validateName)
                staff.lastName = getText(rootView,R.id.last_name, InputFormatterUtils::formatName, Validation::validateName, Constants.EMPTY)!!
                staff.gender = getGender(rootView,R.id.gender_male_radio_group_button)
                staff.contactNumber = getText(rootView,R.id.contact_number, InputFormatterUtils::formatContactNumberFromEdittext, Validation::validateContactNumber)
                staff.dateOfBirth = getDate(rootView,R.id.date_of_birth, InputFormatterUtils::formatString, Validation::validateDate)
                staff.address = getText(rootView,R.id.address, InputFormatterUtils::formatString, Validation::validateString)
                val credentials = getCredentials(rootView)
                credentials.role = Role.STAFF
                return StaffDetails(staff = staff, staffCredentials = credentials, staffStatus = StaffStatus())
            }catch (e: Exception){
                Log.e("IndividualFormUtils","Validation failed",e)
                throw e
            }
        }

        //DONE
        fun getCredentials(rootView: View): Credentials {
            try{
                val credentials = Credentials()
                credentials.username = getText(rootView,R.id.username, InputFormatterUtils::formatString, Validation::validateString, Constants.EMPTY)!!
                credentials.password = getText(rootView,R.id.password, InputFormatterUtils::formatString, Validation::validateString, Constants.EMPTY)!!
                return credentials
            }catch (e: Exception){
                Log.e("IndividualFormUtils","Validation failed",e)
                throw e
            }
        }

        fun getDischargeCard(rootView: View):DischargeCard {
            try{
                val dischargeCard = DischargeCard()
                dischargeCard.firstName = getText(rootView,R.id.first_name, InputFormatterUtils::formatName, Validation::validateName, Constants.EMPTY)!!
                dischargeCard.middleName = getText(rootView,R.id.middle_name, InputFormatterUtils::formatName, Validation::validateName, Constants.EMPTY)!!
                dischargeCard.lastName = getText(rootView,R.id.last_name, InputFormatterUtils::formatName, Validation::validateName, Constants.EMPTY)!!
                dischargeCard.gender = getGender(rootView,R.id.gender_male_radio_group_button)
                dischargeCard.contactNumber = getText(rootView,R.id.contact_number, InputFormatterUtils::formatContactNumberFromEdittext, Validation::validateName, Constants.EMPTY)!!
                dischargeCard.dateOfBirth = getDate(rootView,R.id.date_of_birth, InputFormatterUtils::formatString, Validation::validateName)!!
                dischargeCard.address = getText(rootView,R.id.address, InputFormatterUtils::formatString, Validation::validateName, Constants.EMPTY)!!
                dischargeCard.ageInText = getText(rootView,R.id.age, InputFormatterUtils::formatString, Validation::validateName, Constants.EMPTY)!!
                dischargeCard.dateOfAdmission = getDate(rootView,R.id.date_of_admission, InputFormatterUtils::formatString, Validation::validateName)!!
                dischargeCard.dateOfDischarge = getDate(rootView,R.id.date_of_discharge, InputFormatterUtils::formatString, Validation::validateName)!!
                dischargeCard.diagnosis = getText(rootView,R.id.diagnosis, InputFormatterUtils::formatString, Validation::validateString, Constants.EMPTY)!!
                dischargeCard.patientHistory = getText(rootView,R.id.patient_history, InputFormatterUtils::formatString, Validation::validateName, Constants.EMPTY)!!
                dischargeCard.course = getMedicineTemplates(getView(R.id.course_list, rootView)).map { it.templateData }
                dischargeCard.medicationsOnDischarge = getMedicineTemplates(getView(R.id.medication_list, rootView)).map { it.templateData }
                dischargeCard.advice = getMedicineTemplates(getView(R.id.advice_list, rootView)).map { it.templateData }
                return dischargeCard
            }catch (e: Exception){
                Log.e("IndividualFormUtils","Validation failed",e)
                throw e
            }
        }

        fun getMedicineTemplate(rootView: View):MedicineTemplate {
            try{
                val medicineTemplate = MedicineTemplate()
                medicineTemplate.templateData = getText(rootView,R.id.item_value, InputFormatterUtils::formatString, Validation::validateString, Constants.EMPTY)!!
                return medicineTemplate
            }catch (e: Exception){
                Log.e("IndividualFormUtils","Validation failed",e)
                throw e
            }
        }

        private fun getBillItem(rootView: View):BillItem {
            try{
                val billItem= BillItem()
                billItem.itemName = getText(rootView,R.id.item_name, InputFormatterUtils::formatString, Validation::validateString, BigInteger.ZERO.toString())!!
                billItem.rate = getInteger(rootView,R.id.rate, InputFormatterUtils::formatNumber, Validation::validateString, BigInteger.ZERO.toString())!!
                billItem.quantity = getInteger(rootView,R.id.quantity, InputFormatterUtils::formatNumber, Validation::validateName, BigInteger.ZERO.toString())!!
                return billItem
            }catch (e: Exception){
                Log.e("IndividualFormUtils","Validation failed",e)
                throw e
            }
        }

        private fun getInteger(view: View, validator: ((field: String?)->Boolean), defaultValue: String? = null): Int?{
            val textField = getText(view, InputFormatterUtils::formatNumber,validator, defaultValue)
            return textField?.toInt()
        }

        private fun getInteger(rootView: View, inputId: Int, validator: ((field: String?)->Boolean), defaultValue: String? = null): Int?{
            val textField = getText(rootView, inputId, InputFormatterUtils::formatString, validator, defaultValue)
            return textField?.toInt()
        }

        private fun getFloat(rootView: View, inputId: Int, validator: ((field: String?)->Boolean), defaultValue: String? = null): Float?{
            val textField = getText(rootView, inputId, InputFormatterUtils::formatString, validator, defaultValue)
            return textField?.toFloat()
        }

        private fun getText(view: View, inputFormatter: ((field: String?)->String)? = null, validator: ((field: String?)->Boolean), defaultValue: String? = null): String?{
            val inputEditText = view as TextView
            var textField = inputEditText.text.toString()
            if(Objects.nonNull(inputFormatter)){
                textField = inputFormatter!!(textField)
            }

            return getValidatedText(textField, validator,defaultValue)
        }

        private fun getValidatedText(field: String?, validator: ((field: String?)->Boolean), defaultValue: String? = null): String?{
            return if(validator(field)) field else defaultValue
        }

        private fun getInteger(rootView: View, inputId: Int, inputFormatter: ((field: String?)->String)? = null, validator: ((field: String?)->Boolean), defaultValue: String? = null): Int?{
            val inputEditText = rootView.findViewById<EditText>(inputId)
            return getText(inputEditText, inputFormatter,validator, defaultValue)?.toInt()
        }


        private fun getText(rootView: View, inputId: Int, inputFormatter: ((field: String?)->String)? = null, validator: ((field: String?)->Boolean), defaultValue: String? = null): String?{
            val inputEditText = rootView.findViewById<EditText>(inputId)
            return getText(inputEditText, inputFormatter,validator, defaultValue)
        }

        private fun getTextFromTextView(rootView: View, inputId: Int, inputFormatter: ((field: String?)->String)? = null, validator: ((field: String?)->Boolean), defaultValue: String? = null): String?{
            val inputEditText = rootView.findViewById<TextView>(inputId)
            return getText(inputEditText, inputFormatter,validator, defaultValue)
        }

        private fun <T> getDate(rootView: View, inputId: Int, inputFormatter: ((field: String?)->String)? = null, clazz: Class<T>,validator: ((field: String?)->Boolean), defaultValue: T? = null): T?{
            val inputEditText = rootView.findViewById<EditText>(inputId)
            var dateValue = inputEditText.text.toString()
            if(Objects.nonNull(inputFormatter)){
                dateValue = inputFormatter!!(dateValue)
            }
            return if(validator(dateValue)) DateTimeUtils.getTimestampFromDate(dateValue, clazz) else defaultValue
        }

        private fun getDate(rootView: View, inputId: Int, inputFormatter: ((field: String?)->String)? = null, validator: ((field: String?)->Boolean), defaultValue: LocalDateTime? = null): LocalDateTime?{
            val inputEditText = rootView.findViewById<TextView>(inputId)
            var dateValue = inputEditText.text.toString()
            if(Objects.nonNull(inputFormatter)){
                dateValue = inputFormatter!!(dateValue)
            }
            return if(validator(dateValue)) DateTimeUtils.getLocalDateTimeFromDate(dateValue) else defaultValue
        }

        private fun getGender(rootView: View, inputId: Int): Gender{
            val isInputChecked = rootView.findViewById<RadioButton>(inputId).isChecked
            return if(isInputChecked) Gender.MALE else Gender.FEMALE
        }

        private fun getTime(rootView: View, inputId: Int, inputFormatter: ((field: String?)->String)? = null,validator: ((field: String?)->Boolean), defaultValue: String? = null): String?{
            val inputEditText = rootView.findViewById<EditText>(inputId)
            var textField = inputEditText.text.toString()
            if(Objects.nonNull(inputFormatter)){
                textField = inputFormatter!!(textField)
            }
            return if(validator(textField)) textField else defaultValue
        }

        fun<T:View> getView(viewId: Int, rootView: View):T{
            return rootView.findViewById(viewId)
        }
    }
}