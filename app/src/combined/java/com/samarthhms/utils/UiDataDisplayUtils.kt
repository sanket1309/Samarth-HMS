package com.samarthhms.utils

import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.samarthhms.R
import com.samarthhms.constants.Gender
import com.samarthhms.databinding.BillItemLayoutBinding
import com.samarthhms.models.*
import com.samarthhms.ui.BillAdapter
import com.samarthhms.ui.MedicineTemplateListAdapter
import com.samarthhms.ui.OnUpdateBillSumListener
import java.util.*

class UiDataDisplayUtils {
    companion object{
        private fun<T: Name> displayFullName(rootView: View, name: T){
            try {
                setTextView(name, R.id.name, rootView, OutputFormatterUtils::formatFullName)
            }catch (e: Exception){
                Log.e("UiDataDisplayUtils","Exception while displaying full name")
                throw e
            }
        }

        private fun<T: Name> displayName(rootView: View, name: T){
            try {
                setTextView(name.firstName, R.id.first_name, rootView, OutputFormatterUtils::formatName)
                setTextView(name.middleName, R.id.middle_name, rootView, OutputFormatterUtils::formatName)
                setTextView(name.lastName, R.id.last_name, rootView, OutputFormatterUtils::formatName)
            }catch (e: Exception){
                Log.e("UiDataDisplayUtils","Exception while displaying name")
                throw e
            }
        }

        private fun<T: IndividualInfo> displayIndividualForm(rootView: View, individualInfo: T, isGenderRadioButton: Boolean = true){
            try {
                displayName(rootView, individualInfo)
                displayGender(rootView, individualInfo.gender == Gender.MALE, isGenderRadioButton)
                setTextView(individualInfo.contactNumber, R.id.contact_number, rootView, OutputFormatterUtils::formatContactNumberForEditText)
                setTextView(individualInfo.dateOfBirth, R.id.date_of_birth, rootView, OutputFormatterUtils::formatDateForEdittext)
            }catch (e: Exception){
                Log.e("UiDataDisplayUtils","Exception while displaying individual form")
                throw e
            }
        }

        private fun<T: IndividualInfoWithLocation> displayIndividualFormWithLocation(rootView: View, individualInfo: T){
            try {
                displayIndividualForm(rootView, individualInfo)
                setTextView(individualInfo.town, R.id.town, rootView, OutputFormatterUtils::formatString)
                setTextView(individualInfo.taluka, R.id.taluka, rootView, OutputFormatterUtils::formatString)
                setTextView(individualInfo.district, R.id.district, rootView, OutputFormatterUtils::formatString)
            }catch (e: Exception){
                Log.e("UiDataDisplayUtils","Exception while displaying individual form with location")
                throw e
            }
        }

        fun<T: IndividualInfoWithAddress> displayIndividualFormWithAddress(rootView: View, individualInfo: T){
            try {
                displayIndividualForm(rootView, individualInfo)
                setTextView(individualInfo.address, R.id.address, rootView, OutputFormatterUtils::formatString)
            }catch (e: Exception){
                Log.e("UiDataDisplayUtils","Exception while displaying individual form with address")
                throw e
            }
        }

        fun displayBillItem(billItemLayoutBinding: BillItemLayoutBinding, billItem: BillItem, displayItemName: Boolean = false){
            try {
                if(displayItemName) billItemLayoutBinding.itemName.setText(billItem.itemName)
                billItemLayoutBinding.rate.setText(billItem.rate.toString())
                billItemLayoutBinding.quantity.setText(billItem.quantity.toString())
                billItemLayoutBinding.sum.setText((billItem.quantity * billItem.rate).toString())
            }catch (e: Exception){
                Log.e("UiDataDisplayUtils","Exception while displaying bill item")
                throw e
            }
        }

        fun displayStaffDetails(rootView: View, staffDetails: StaffDetails){
            try {
                setTextView(staffDetails.staff, R.id.full_name, rootView, OutputFormatterUtils::formatFullName)
                EditableUtils.updateVisibility(staffDetails.staffStatus?.isLocked?:false, UiDataExtractorUtils.getView(R.id.self_title, rootView), true)
                EditableUtils.updateVisibility(!(staffDetails.staffStatus?.isLocked?:false), UiDataExtractorUtils.getView(R.id.switch_status, rootView), true)
            }catch (e: Exception){
                Log.e("UiDataDisplayUtils","Exception while displaying staff details")
                throw e
            }
        }

        fun displaySwitchAdmin(rootView: View, switchAdminData: SwitchAdminData){
            try {
                setTextView(switchAdminData.admin, R.id.full_name, rootView, OutputFormatterUtils::formatFullName)
                EditableUtils.updateVisibility(switchAdminData.isAccountOwner, UiDataExtractorUtils.getView(R.id.self_title, rootView))
                EditableUtils.updateVisibility(switchAdminData.isCurrentUser, UiDataExtractorUtils.getView(R.id.switch_status, rootView))
            }catch (e: Exception){
                Log.e("UiDataDisplayUtils","Exception while displaying admin details")
                throw e
            }
        }

        fun displayAdminDetails(rootView: View, adminDetails: AdminDetails){
            try {
                setTextView(adminDetails.admin, R.id.full_name, rootView, OutputFormatterUtils::formatFullName)
                EditableUtils.updateVisibility(adminDetails.switchAdminData?.isAccountOwner?:false, UiDataExtractorUtils.getView(R.id.self_title, rootView))
                EditableUtils.updateVisibility(adminDetails.switchAdminData?.isCurrentUser?:false, UiDataExtractorUtils.getView(R.id.switch_status, rootView))
            }catch (e: Exception){
                Log.e("UiDataDisplayUtils","Exception while displaying admin details")
                throw e
            }
        }

        fun displayPatient(rootView: View, patient: Patient){
            try {
                setTextView(patient.patientId, R.id.address, rootView, OutputFormatterUtils::formatString)
                displayIndividualFormWithLocation(rootView, patient)
            }catch (e: Exception){
                Log.e("UiDataDisplayUtils","Exception while displaying patient")
                throw e
            }
        }

        fun displayAdmitPatientInfoItem(rootView: View, admitPatientInfo: AdmitPatientInfo){
            try {
                displayFullName(rootView, admitPatientInfo)
                displayGender(rootView, admitPatientInfo.gender == Gender.MALE,false, OutputFormatterUtils::formatGenderForPatientItem)
                setTextView(admitPatientInfo, R.id.age, rootView, OutputFormatterUtils::formatPatientAgeForListItem)
                setTextView(admitPatientInfo, R.id.address, rootView, OutputFormatterUtils::formatLocationForListItem)
                setTextView(admitPatientInfo.diagnosis, R.id.diagnosis, rootView, OutputFormatterUtils::formatString)
            }catch (e: Exception){
                Log.e("UiDataDisplayUtils","Exception while displaying admit patient info item")
                throw e
            }
        }

        fun displayBillListItem(rootView: View, bill: Bill){
            try {
                setTextView(bill.billNumber, R.id.bill_number, rootView, OutputFormatterUtils::formatYearWiseId)
                displayAdmitPatientInfoItem(rootView, bill)
            }catch (e: Exception){
                Log.e("UiDataDisplayUtils","Exception while displaying bill list item")
                throw e
            }
        }

        fun displayDischargeCard(rootView: View, dischargeCard: DischargeCard){
            try {
                setTextView(dischargeCard.ipdNumber, R.id.ipd_number, rootView, OutputFormatterUtils::formatYearWiseId)
                displayAdmitPatientInfoItem(rootView, dischargeCard)
                setTextView(dischargeCard.dateOfAdmission, R.id.date_of_admission, rootView, OutputFormatterUtils::formatDateForEdittext)
                setTextView(dischargeCard.dateOfAdmission, R.id.time_of_admission, rootView, OutputFormatterUtils::formatTime)
                setTextView(dischargeCard.dateOfDischarge, R.id.date_of_discharge, rootView, OutputFormatterUtils::formatDateForEdittext)
                setTextView(dischargeCard.dateOfDischarge, R.id.time_of_discharge, rootView, OutputFormatterUtils::formatTime)
                setTextView(dischargeCard.patientHistory, R.id.patient_history, rootView, OutputFormatterUtils::formatString)
                setTextView(dischargeCard.pastHistory, R.id.past_history, rootView, OutputFormatterUtils::formatString)
                setTextView(dischargeCard.familyHistory, R.id.family_history, rootView, OutputFormatterUtils::formatString)
                setTextView(dischargeCard.investigations, R.id.investigations, rootView, OutputFormatterUtils::formatString)
                setTextView(dischargeCard.weightInKg, R.id.investigations, rootView, OutputFormatterUtils::formatNumber)

                setAdapter(R.id.course_list, rootView, dischargeCard.course, MedicineTemplateListAdapter::class.java)
                setAdapter(R.id.medication_list, rootView, dischargeCard.course, MedicineTemplateListAdapter::class.java)
                setAdapter(R.id.advice_list, rootView, dischargeCard.course, MedicineTemplateListAdapter::class.java)
            }catch (e: Exception){
                Log.e("UiDataDisplayUtils","Exception while displaying discharge card list item")
                throw e
            }
        }

        fun displayDischargeCard(rootView: View, bill: Bill){
            try {
                setTextView(bill.billNumber, R.id.bill_number, rootView, OutputFormatterUtils::formatYearWiseId)
                displayAdmitPatientInfoItem(rootView, bill)
                setTextView(bill.dateOfAdmission, R.id.date_of_admission, rootView, OutputFormatterUtils::formatDateForEdittext)
                setTextView(bill.dateOfAdmission, R.id.time_of_admission, rootView, OutputFormatterUtils::formatTime)
                setTextView(bill.dateOfDischarge, R.id.date_of_discharge, rootView, OutputFormatterUtils::formatDateForEdittext)
                setTextView(bill.dateOfDischarge, R.id.time_of_discharge, rootView, OutputFormatterUtils::formatTime)
                setTextView(bill.otherCharges, R.id.other_charges, rootView)//TODO ADD OUTPUT FORMATTER

                setAdapter(R.id.treatment_charges, rootView, bill.treatmentCharges, BillAdapter::class.java)
                setAdapter(R.id.management_charges, rootView, bill.managementCharges, BillAdapter::class.java)
            }catch (e: Exception){
                Log.e("UiDataDisplayUtils","Exception while displaying discharge card list item")
                throw e
            }
        }

        fun <T: RecyclerViewAdapter<VH,D>,VH,D> setAdapter(recyclerViewId: Int, rootView: View, data: List<D>?,clazz: Class<T>){
            val recyclerView = UiDataExtractorUtils.getView<RecyclerView>(recyclerViewId, rootView)
            setAdapter(recyclerView, data, clazz)
        }

        fun <T: RecyclerViewAdapter<VH,D>,VH,D> setAdapter(recyclerView: RecyclerView, data: List<D>?,clazz: Class<T>){
            val adapter = clazz.getConstructor().newInstance(data)
            data?.let { adapter.updateData(data) }
            recyclerView.adapter = adapter
        }

        fun displayDischargeCardListItem(rootView: View, dischargeCard: DischargeCard){
            try {
                setTextView(dischargeCard.ipdNumber, R.id.ipd_number, rootView, OutputFormatterUtils::formatYearWiseId)
                displayAdmitPatientInfoItem(rootView, dischargeCard)
            }catch (e: Exception){
                Log.e("UiDataDisplayUtils","Exception while displaying discharge card list item")
                throw e
            }
        }

        fun displayPatientVisitInfo(rootView: View, patientVisitInfo: PatientVisitInfo){
            try {
                displayPatient(rootView, patientVisitInfo.patient)
                setTextView(patientVisitInfo.visitTime, R.id.visit_time, rootView, OutputFormatterUtils::formatTime)
            }catch (e: Exception){
                Log.e("UiDataDisplayUtils","Exception while displaying discharge card list item")
                throw e
            }
        }

        fun displayPatientItem(rootView: View, patient: Patient){
            try {
                setTextView(patient.patientId, R.id.address, rootView, OutputFormatterUtils::formatString)
                displayFullName(rootView, patient)
                displayGender(rootView, patient.gender == Gender.MALE,false, OutputFormatterUtils::formatGenderForPatientItem)
                setTextView(patient, R.id.age, rootView, OutputFormatterUtils::formatPatientAgeForListItem)
                setTextView(patient, R.id.address, rootView, OutputFormatterUtils::formatLocationForListItem)
            }catch (e: Exception){
                Log.e("UiDataDisplayUtils","Exception while displaying patient item")
                throw e
            }
        }

        fun displayPatientHistoryTemplate(rootView: View, patientHistoryTemplate: PatientHistoryTemplate){
            try {
                setEditText(patientHistoryTemplate.templateName, R.id.template_name, rootView, OutputFormatterUtils::formatString)
                setEditText(patientHistoryTemplate.templateData, R.id.template, rootView, OutputFormatterUtils::formatString)
            }catch (e: Exception){
                Log.e("UiDataDisplayUtils","Exception while displaying medicine template")
                throw e
            }
        }

        fun displaySearchItem(rootView: View, value: String){
            try {
                setEditText(value, R.id.item_value, rootView, OutputFormatterUtils::formatString)
            }catch (e: Exception){
                Log.e("UiDataDisplayUtils","Exception while displaying search item")
                throw e
            }
        }

        fun displayMedicineTemplate(rootView: View, templateData: String?){
            try {
                setEditText(templateData, R.id.template, rootView, OutputFormatterUtils::formatString)
            }catch (e: Exception){
                Log.e("UiDataDisplayUtils","Exception while displaying medicine template")
                throw e
            }
        }

        fun updateBillItemSum(billItemLayoutBinding: BillItemLayoutBinding,onUpdateBillSumListener: OnUpdateBillSumListener, isNewlyAdded: Boolean){
            try {
                val billItem = UiDataExtractorUtils.extractData(billItemLayoutBinding)
                var previousSum = billItem.sum
                val sum = billItem.rate * billItem.quantity
                billItemLayoutBinding.sum.setText(sum.toString())
                if(isNewlyAdded) previousSum = 0
                onUpdateBillSumListener.update(sum-previousSum)
            }catch (e: Exception){
                Log.e("UiDataDisplayUtils","Exception while displaying patient")
                throw e
            }
        }

        fun displayCharges(rootView: View, charges: Int){
            try {
//                TODO CHANGE THIS ID
                setTextView(charges.toString(), R.id.other_charges, rootView, OutputFormatterUtils::formatString)
            }catch (e: Exception){
                Log.e("UiDataDisplayUtils","Exception while displaying patient")
                throw e
            }
        }

        fun displayCredentialDetails(rootView: View, credentials: Credentials){
            try {
                setTextView(credentials.username, R.id.username, rootView, OutputFormatterUtils::formatString)
                setTextView(credentials.password, R.id.password, rootView, OutputFormatterUtils::formatString)
            }catch (e: Exception){
                Log.e("UiDataDisplayUtils","Exception while displaying credentials")
                throw e
            }
        }

        fun displayGender(rootView: View, isMale: Boolean, isRadioButton: Boolean, outputFormatter: ((output: String?)->String)? = null){
            try {
                if(isRadioButton){
                    checkRadioButton(rootView, R.id.gender_male_radio_group_button, isMale)
                }else{
                    val gender = if(isMale) Gender.MALE else Gender.FEMALE
                    setTextView(gender.value, R.id.gender, rootView,outputFormatter)
                }
            }catch (e: Exception){
                Log.e("UiDataDisplayUtils","Exception while displaying gender")
                throw e
            }
        }

        private fun checkRadioButton(rootView: View, radioButtonId: Int, isChecked: Boolean){
            val radioButton = rootView.findViewById<RadioButton>(radioButtonId)
            radioButton.isChecked = isChecked
        }

        private fun<T,V :TextView> setText(value: T?, rootView: V,outputFormatter: ((output: T?)->String)? = null){
            val textView = rootView
            var formattedValue = value.toString()
            if(Objects.nonNull(outputFormatter)){
                formattedValue = outputFormatter!!(value)
            }
            textView.setText(formattedValue)
        }

        private fun<T> setTextView(value: T?, textView: TextView, outputFormatter: ((output: T?)->String)? = null){
            setText(value, textView, outputFormatter)
        }

        private fun<T> setEditText(value: T?, editText: EditText, outputFormatter: ((output: T?)->String)? = null){
            setText(value, editText, outputFormatter)
        }

        private fun<T,V :TextView> setText(value: T?, textViewId: Int, rootView: View, clazz: Class<V> ,outputFormatter: ((output: T?)->String)? = null){
            val textView = rootView.findViewById<V>(textViewId)
            setText(value, textView, outputFormatter)
        }

        private fun<T> setTextView(value: T?, textViewId: Int, rootView: View, outputFormatter: ((output: T?)->String)? = null){
            setText<T,TextView>(value, textViewId, rootView, TextView::class.java, outputFormatter)
        }

        private fun<T> setEditText(value: T?, textViewId: Int, rootView: View, outputFormatter: ((output: T?)->String)? = null){
            setText(value, textViewId, rootView, EditText::class.java, outputFormatter)
        }
    }
}