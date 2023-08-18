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
import androidx.viewbinding.ViewBinding
import com.samarthhms.R
import com.samarthhms.constants.Colors
import com.samarthhms.constants.Gender
import com.samarthhms.databinding.BillItemLayoutBinding
import com.samarthhms.databinding.FragmentDischargeCardTemplateBinding
import com.samarthhms.databinding.FragmentEditDischargeCardBinding
import com.samarthhms.databinding.FragmentGenerateBillBinding
import com.samarthhms.databinding.MedicineTemplateLayoutBinding
import com.samarthhms.models.*
import com.samarthhms.ui.BillAdapter
import com.samarthhms.ui.MedicineTemplateListAdapter
import java.lang.reflect.ParameterizedType
import java.time.LocalDateTime
import java.util.*

class UiDataExtractorUtils {
    companion object{
        fun extractData(billItemLayoutBinding: BillItemLayoutBinding): BillItem {
            try{
                val billItem = BillItem()
                billItem.rate = getInteger(billItemLayoutBinding.rate)
                billItem.quantity = getInteger(billItemLayoutBinding.quantity)
                billItem.sum = getInteger(billItemLayoutBinding.sum)
                return billItem
            }catch (e: Exception){
                Log.e("BillDataExtractorUtils","Failed to extract bill item",e)
                throw e
            }
        }

        fun extractData(billBinding: FragmentGenerateBillBinding): Bill {
            try{
                val bill =getBill(billBinding.root)
                bill.managementCharges = getBillItems(billBinding.managementCharges)
                bill.treatmentCharges = getBillItems(billBinding.treatmentCharges)
                bill.otherCharges =getBillItem(billBinding.otherCharges.root)
                return bill
            }catch (e: Exception){
                Log.e("BillDataExtractorUtils","Failed to extract bill item",e)
                throw e
            }
        }

        fun getMedicineTemplate(medicineTemplateLayoutBinding: MedicineTemplateLayoutBinding): MedicineTemplate {
            try{
                val medicineTemplate = MedicineTemplate()
                medicineTemplate.templateData = getText(medicineTemplateLayoutBinding.template, InputFormatterUtils::formatString)
                return medicineTemplate
            }catch (e: Exception){
                Log.e("BillDataExtractorUtils","Failed to extract bill item",e)
                throw e
            }
        }

        fun getDischargeCard(dischargeCardBinding: FragmentEditDischargeCardBinding): DischargeCard {
            try{
                val dischargeCard = getDischargeCard(dischargeCardBinding.root)
                dischargeCard.course = getMedicineTemplates(dischargeCardBinding.courseList).map { it.templateData }
                dischargeCard.medicationsOnDischarge = getMedicineTemplates(dischargeCardBinding.medicationList).map { it.templateData }
                dischargeCard.advice = getMedicineTemplates(dischargeCardBinding.adviceList).map { it.templateData }
                return dischargeCard
            }catch (e: Exception){
                Log.e("BillDataExtractorUtils","Failed to extract bill item",e)
                throw e
            }
        }

        fun getMedicineTemplates(recyclerView: RecyclerView): List<MedicineTemplate>{
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

        fun getBillItems(recyclerView: RecyclerView): List<BillItem>{
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

        fun getName(rootView: View, name: Name) {
            try{
                name.firstName = getText(rootView,R.id.first_name, InputFormatterUtils::formatName)
                name.middleName = getText(rootView,R.id.middle_name, InputFormatterUtils::formatName)
                name.lastName = getText(rootView,R.id.last_name, InputFormatterUtils::formatName)
            }catch (e: Exception){
                Log.e("IndividualFormUtils","Validation failed for name",e)
                throw e
            }
        }

        fun getLocation(rootView: View, location: Location) {
            try{
                location.town = getText(rootView,R.id.town, InputFormatterUtils::formatString)
                location.taluka = getText(rootView,R.id.taluka, InputFormatterUtils::formatString)
                location.district = getText(rootView,R.id.district, InputFormatterUtils::formatString)
            }catch (e: Exception){
                Log.e("IndividualFormUtils","Validation failed for name",e)
                throw e
            }
        }

        fun getIndividualInfo(rootView: View, individualInfo: IndividualInfo) {
            try{
                getName(rootView, individualInfo)
                individualInfo.gender = getGender(rootView,R.id.gender_male_radio_group_button)
                individualInfo.contactNumber = getText(rootView,R.id.contact_number, InputFormatterUtils::formatContactNumberFromEdittext)
                individualInfo.dateOfBirth = getDate(rootView,R.id.date_of_birth, InputFormatterUtils::formatString)
            }catch (e: Exception){
                Log.e("IndividualFormUtils","Validation failed",e)
                throw e
            }
        }

        fun <T:IndividualInfoWithAddress> extractData(rootView: View, clazz: Class<T>): IndividualFormData<T> {
            try{
                val individualInfoWithAddress = clazz.newInstance()
                getIndividualInfo(rootView, individualInfoWithAddress)
                individualInfoWithAddress.address = getText(rootView,R.id.address, InputFormatterUtils::formatString)
                val credentials = getCredentials(rootView)

                val individualFormData = IndividualFormData<T>()
                individualFormData.data = individualInfoWithAddress
                individualFormData.credentials = credentials

                return individualFormData
            }catch (e: Exception){
                Log.e("IndividualFormUtils","Validation failed",e)
                throw e
            }
        }

        fun getCredentials(rootView: View): Credentials {
            try{
                val credentials = Credentials()
                credentials.username = getText(rootView,R.id.username, InputFormatterUtils::formatString)
                credentials.password = getText(rootView,R.id.password, InputFormatterUtils::formatString)
                return credentials
            }catch (e: Exception){
                Log.e("IndividualFormUtils","Validation failed",e)
                throw e
            }
        }

        fun getIndividualInfoWithAddress(rootView: View, individualInfoWithAddress: IndividualInfoWithAddress) {
            try{
                getIndividualInfo(rootView, individualInfoWithAddress)
                individualInfoWithAddress.address = getText(rootView,R.id.address, InputFormatterUtils::formatString)
            }catch (e: Exception){
                Log.e("IndividualFormUtils","Validation failed",e)
                throw e
            }
        }

        fun getAdmitPatientInfo(rootView: View, admitPatientInfo: AdmitPatientInfo) {
            try{
                getIndividualInfoWithLocation(rootView, admitPatientInfo)
                admitPatientInfo.address = getText(rootView,R.id.address, InputFormatterUtils::formatString)
            }catch (e: Exception){
                Log.e("IndividualFormUtils","Validation failed",e)
                throw e
            }
        }

        fun getDischargeCard(rootView: View):DischargeCard {
            try{
                val dischargeCard = DischargeCard()
                getIndividualInfo(rootView, dischargeCard)
                dischargeCard.address = getText(rootView,R.id.address, InputFormatterUtils::formatString)
                dischargeCard.dateOfAdmission = getDate(rootView,R.id.date_of_admission, InputFormatterUtils::formatString)
                dischargeCard.dateOfDischarge = getDate(rootView,R.id.date_of_discharge, InputFormatterUtils::formatString)
                dischargeCard.diagnosis = getText(rootView,R.id.diagnosis, InputFormatterUtils::formatString)
                dischargeCard.patientHistory = getText(rootView,R.id.patient_history, InputFormatterUtils::formatString)
                return dischargeCard
            }catch (e: Exception){
                Log.e("IndividualFormUtils","Validation failed",e)
                throw e
            }
        }

        fun getMedicineTemplate(rootView: View):MedicineTemplate {
            try{
                val medicineTemplate = MedicineTemplate()
                medicineTemplate.templateData = getText(rootView,R.id.item_value, InputFormatterUtils::formatString)
                return medicineTemplate
            }catch (e: Exception){
                Log.e("IndividualFormUtils","Validation failed",e)
                throw e
            }
        }

        fun getBill(rootView: View):Bill {
            try{
                val bill= Bill()
                getIndividualInfo(rootView, bill)
                bill.address = getText(rootView,R.id.address, InputFormatterUtils::formatString)
                bill.dateOfAdmission = getDate(rootView,R.id.date_of_admission, InputFormatterUtils::formatString)
                bill.dateOfDischarge = getDate(rootView,R.id.date_of_discharge, InputFormatterUtils::formatString)
                bill.diagnosis = getText(rootView,R.id.diagnosis, InputFormatterUtils::formatString)
                return bill
            }catch (e: Exception){
                Log.e("IndividualFormUtils","Validation failed",e)
                throw e
            }
        }

        fun getBillItem(rootView: View):BillItem {
            try{
                val billItem= BillItem()
                billItem.itemName = getText(rootView,R.id.item_name, InputFormatterUtils::formatString)
                billItem.rate = getInteger(rootView,R.id.rate, InputFormatterUtils::formatNumber)
                billItem.quantity = getInteger(rootView,R.id.quantity, InputFormatterUtils::formatNumber)
                return billItem
            }catch (e: Exception){
                Log.e("IndividualFormUtils","Validation failed",e)
                throw e
            }
        }

        fun getIndividualInfoWithLocation(rootView: View, individualInfoWithLocation: IndividualInfoWithLocation) {
            try{
                getIndividualInfo(rootView, individualInfoWithLocation)
                getLocation(rootView, individualInfoWithLocation)
            }catch (e: Exception){
                Log.e("IndividualFormUtils","Validation failed",e)
                throw e
            }
        }

        public fun getInteger(view: View): Int{
            val textField = getText(view, InputFormatterUtils::formatNumber)
            return textField.toInt()
        }

        public fun getInteger(rootView: View, inputId: Int): Int{
            val textField = getText(rootView, inputId, InputFormatterUtils::formatString)
            return textField.toInt()
        }

        public fun getFloat(rootView: View, inputId: Int): Float{
            val textField = getText(rootView, inputId, InputFormatterUtils::formatString)
            return textField.toFloat()
        }

        public fun getText(view: View, inputFormatter: ((field: String?)->String)? = null): String{
            val inputEditText = view as EditText
            var textField = inputEditText.text.toString()
            if(Objects.nonNull(inputFormatter)){
                textField = inputFormatter!!(textField)
            }
            return textField
        }

        public fun getInteger(rootView: View, inputId: Int, inputFormatter: ((field: String?)->String)? = null): Int{
            val inputEditText = rootView.findViewById<EditText>(inputId)
            return getText(inputEditText, inputFormatter).toInt()
        }


        public fun getText(rootView: View, inputId: Int, inputFormatter: ((field: String?)->String)? = null): String{
            val inputEditText = rootView.findViewById<EditText>(inputId)
            return getText(inputEditText, inputFormatter)
        }

        public fun <T> getDate(rootView: View, inputId: Int, inputFormatter: ((field: String?)->String)? = null, clazz: Class<T>): T?{
            val inputEditText = rootView.findViewById<EditText>(inputId)
            var dateValue = inputEditText.text.toString()
            if(Objects.nonNull(inputFormatter)){
                dateValue = inputFormatter!!(dateValue)
            }
            return DateTimeUtils.getTimestampFromDate(dateValue, clazz)
        }

        public fun getDate(rootView: View, inputId: Int, inputFormatter: ((field: String?)->String)? = null): LocalDateTime?{
            val inputEditText = rootView.findViewById<EditText>(inputId)
            var dateValue = inputEditText.text.toString()
            if(Objects.nonNull(inputFormatter)){
                dateValue = inputFormatter!!(dateValue)
            }
            return DateTimeUtils.getLocalDateTimeFromDate(dateValue)
        }

        public fun getGender(rootView: View, inputId: Int): Gender{
            val isInputChecked = rootView.findViewById<RadioButton>(inputId).isChecked
            return if(isInputChecked) Gender.MALE else Gender.FEMALE
        }

        public fun getTime(rootView: View, inputId: Int, inputFormatter: ((field: String?)->String)? = null): String{
            val inputEditText = rootView.findViewById<EditText>(inputId)
            var textField = inputEditText.text.toString()
            if(Objects.nonNull(inputFormatter)){
                textField = inputFormatter!!(textField)
            }
            return textField
        }

        fun<T:View> getView(viewId: Int, rootView: View):T{
            return rootView.findViewById(viewId)
        }


        public fun validateTextField(textField: String, validationFunction: (field: String)->Boolean, fieldTitle: TextView, field: EditText, context: Context, resources: Resources){
            if(!validationFunction(textField)){
                InputFieldColorUtils.changeColorOfInputFields(fieldTitle, field, Colors.Validation.INVALID, context, resources)
                Toast.makeText(context, "Invalid "+fieldTitle.text.toString(),Toast.LENGTH_SHORT).show()
                throw Exception("Text validation failed")
            }else{
                InputFieldColorUtils.changeColorOfInputFields(fieldTitle, field, Colors.Validation.INVALID, context, resources)
            }
        }
    }
}