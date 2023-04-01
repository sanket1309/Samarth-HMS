package com.samarthhms.ui

import android.Manifest
import android.R.attr.path
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.DrawableContainer
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.StateListDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.samarthhms.BuildConfig
import com.samarthhms.R
import com.samarthhms.constants.Gender
import com.samarthhms.databinding.FragmentGenerateDischargeCardBinding
import com.samarthhms.domain.Status
import com.samarthhms.models.DischargeCard
import com.samarthhms.models.DischargeCardTemplate
import com.samarthhms.models.MedicineTemplate
import com.samarthhms.models.PatientHistoryTemplate
import com.samarthhms.utils.DateTimeUtils
import com.samarthhms.utils.IdUtils
import com.samarthhms.utils.StringUtils
import com.samarthhms.utils.Validation
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class GenerateDischargeCardFragment : Fragment(), RecyclerOnItemViewClickListener {

    private val viewModel: GenerateDischargeCardViewModel by viewModels()

    private lateinit var binding: FragmentGenerateDischargeCardBinding

    private var bottomSheetDialog: BottomSheetDialog? = null

    var dischargeCardTemplate: DischargeCardTemplate? = null

    var COURSE_MEDICINE_TEMPLATE_REQUESTER = "COURSE_MEDICINE_TEMPLATE_REQUESTER"

    var MEDICATION_MEDICINE_TEMPLATE_REQUESTER = "MEDICATION_MEDICINE_TEMPLATE_REQUESTER"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGenerateDischargeCardBinding.inflate(layoutInflater, container, false)
        binding.courseList.adapter = MedicineTemplateListAdapter(mutableListOf())
        binding.medicationList.adapter = MedicineTemplateListAdapter(mutableListOf())
        binding.adviceList.adapter = MedicineTemplateListAdapter(mutableListOf())

        startProgressBar(true)
        viewModel.getData()

        viewModel.dischargeCardTemplate.observe(viewLifecycleOwner){
            if(viewModel.getDischargeCardTemplateStatus.value == Status.SUCCESS){
                dischargeCardTemplate = viewModel.dischargeCardTemplate.value
                startProgressBar(false)
            }else if(viewModel.getDischargeCardTemplateStatus.value == Status.FAILURE){
                dischargeCardTemplate = viewModel.dischargeCardTemplate.value
                startProgressBar(false)
                Toast.makeText(activity, "Something Went Wrong", Toast.LENGTH_SHORT).show()
            }
        }

        binding.addPatientHistoryButton.setOnClickListener{
            if(dischargeCardTemplate!=null){
                val adapter = PatientHistoryTemplateSearchAdapter(this, dischargeCardTemplate!!.patientHistoryTemplates)
                popTemplateMenu(adapter)
            }
        }

        binding.addCourseButton.setOnClickListener{
            if(dischargeCardTemplate!=null){
                val adapter = MedicineTemplateSearchAdapter(this, dischargeCardTemplate!!.medicineTemplates, COURSE_MEDICINE_TEMPLATE_REQUESTER)
                popTemplateMenu(adapter)
            }
        }

        binding.addMedicationButton.setOnClickListener{
            if(dischargeCardTemplate!=null){
                val adapter = MedicineTemplateSearchAdapter(this, dischargeCardTemplate!!.medicineTemplates, MEDICATION_MEDICINE_TEMPLATE_REQUESTER)
                popTemplateMenu(adapter)
            }
        }

        binding.addAdviceButton.setOnClickListener{
            if(dischargeCardTemplate!=null){
                val templates = (binding.adviceList.adapter as MedicineTemplateListAdapter).templates
                templates.add(MedicineTemplate("",""))
                (binding.adviceList.adapter as MedicineTemplateListAdapter).notifyItemInserted(templates.size-1)
                bottomSheetDialog?.cancel()
            }
        }

        binding.generateDischargeCardButton.setOnClickListener{
            startProgressBar(true)
            val dischargeCard = getDischargeCard()
            if(Objects.nonNull(dischargeCard)){
                viewModel.makeDischargeCard(dischargeCard!!)
            }else{
                startProgressBar(false)
            }
        }

        ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE), 2)

        viewModel.getDischargeCardStatus.observe(viewLifecycleOwner){
            if(it == Status.NONE){
                startProgressBar(false)
                return@observe
            }
            if(PackageManager.PERMISSION_GRANTED != context?.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ||
                PackageManager.PERMISSION_GRANTED != context?.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                return@observe
            }
            if(it == Status.SUCCESS && viewModel.dischargeCardFile.value != null){
                startProgressBar(false)
                val action = GenerateDischargeCardFragmentDirections.actionGenerateDischargeCardFragmentToPdfDetailsFragment(viewModel.dischargeCardFile.value!!)
                findNavController().navigate(action)
            }
        }
        return binding.root
    }

    private fun getDischargeCard(): DischargeCard? {
        val invalidColor = R.color.red
        val validColor = R.color.blue_theme

        val patientId = IdUtils.PATIENT_ID_PREFIX + binding.patientId.text
        if (!Validation.validatePatientId(patientId)) {
            changeTextColorOfTextView(binding.patientIdTitle, invalidColor)
            changeBorderColorOfPrefix(binding.patientIdPrefix, invalidColor)
            changeBorderColorOfSuffix(binding.patientId, invalidColor)
            return null
        }else{
            changeTextColorOfTextView(binding.patientIdTitle, validColor)
            changeBorderColorOfPrefix(binding.patientIdPrefix, validColor)
            changeBorderColorOfSuffix(binding.patientId, validColor)
        }

        val ipdNumber = binding.ipdNumber.text.toString()
        if (!Validation.validateIpdNumber(ipdNumber)) {
            changeTextColorOfTextView(binding.ipdNumberTitle, invalidColor)
            changeBorderColorOfEditText(binding.ipdNumber, invalidColor)
            binding.ipdNumber.addTextChangedListener {  }
            return null
        }else{
            changeTextColorOfTextView(binding.ipdNumberTitle, validColor)
            changeBorderColorOfEditText(binding.ipdNumber, validColor)
        }

        val firstName = binding.firstName.text.toString()
        if (!Validation.validateName(firstName)) {
            changeTextColorOfTextView(binding.firstNameTitle, invalidColor)
            changeBorderColorOfEditText(binding.firstName, invalidColor)
            return null
        }else{
            changeTextColorOfTextView(binding.firstNameTitle, validColor)
            changeBorderColorOfEditText(binding.firstName, validColor)
        }

        val middleName = binding.middleName.text.toString().trim()
        if (!Validation.validateName(middleName)) {
            changeTextColorOfTextView(binding.middleNameTitle, invalidColor)
            changeBorderColorOfEditText(binding.middleName, invalidColor)
            return null
        }else{
            changeTextColorOfTextView(binding.middleNameTitle, validColor)
            changeBorderColorOfEditText(binding.middleName, validColor)
        }

        val lastName = binding.lastName.text.toString()
        if (!Validation.validateName(lastName)) {
            changeTextColorOfTextView(binding.lastNameTitle, invalidColor)
            changeBorderColorOfEditText(binding.lastName, invalidColor)
            return null
        }else{
            changeTextColorOfTextView(binding.lastNameTitle, validColor)
            changeBorderColorOfEditText(binding.lastName, validColor)
        }

        val gender = if(binding.genderMaleRadioGroupButton.isChecked) Gender.MALE else Gender.FEMALE

        val weightVal = binding.weight.text.toString()
        val weight = if(weightVal.isEmpty()) 0f else weightVal.toFloat()
        if (weight <= 0f) {
            changeTextColorOfTextView(binding.weightTitle, invalidColor)
            changeBorderColorOfEditText(binding.weight, invalidColor)
            return null
        }else{
            changeTextColorOfTextView(binding.weightTitle, validColor)
            changeBorderColorOfEditText(binding.weight, validColor)
        }

        val heightVal = binding.height.text.toString()
        val height = if(heightVal.isEmpty()) 0f else heightVal.toFloat()
        if (height <= 0f) {
            changeTextColorOfTextView(binding.heightTitle, invalidColor)
            changeBorderColorOfEditText(binding.height, invalidColor)
            return null
        }else{
            changeTextColorOfTextView(binding.heightTitle, validColor)
            changeBorderColorOfEditText(binding.height, validColor)
        }

        val age = binding.age.text.toString()
        if (age.isEmpty()) {
            changeTextColorOfTextView(binding.ageTitle, invalidColor)
            changeBorderColorOfEditText(binding.age, invalidColor)
            return null
        }else{
            changeTextColorOfTextView(binding.ageTitle, validColor)
            changeBorderColorOfEditText(binding.age, validColor)
        }

        val dob = binding.dateOfBirth.text.toString()
        if (!Validation.validateDate(dob)) {
            changeTextColorOfTextView(binding.dateOfBirthTitle, invalidColor)
            changeBorderColorOfEditText(binding.dateOfBirth, invalidColor)
            return null
        }else{
            changeTextColorOfTextView(binding.dateOfBirthTitle, validColor)
            changeBorderColorOfEditText(binding.dateOfBirth, validColor)
        }

        val contactNumber = binding.contactNumber.text.toString().replace(" ","")
        if (!Validation.validateContactNumber(contactNumber)) {
            changeTextColorOfTextView(binding.contactNumberTitle, invalidColor)
            changeBorderColorOfEditText(binding.contactNumber, invalidColor)
            return null
        }else{
            changeTextColorOfTextView(binding.contactNumberTitle, validColor)
            changeBorderColorOfEditText(binding.contactNumber, validColor)
        }

        val address = binding.address.text.toString()
        if (address.isEmpty()) {
            changeTextColorOfTextView(binding.addressTitle, invalidColor)
            changeBorderColorOfEditText(binding.address, invalidColor)
            return null
        }else{
            changeTextColorOfTextView(binding.addressTitle, validColor)
            changeBorderColorOfEditText(binding.address, validColor)
        }

        val doa = binding.dateOfAdmission.text.toString()
        if (!Validation.validateDate(doa)) {
            changeTextColorOfTextView(binding.dateOfAdmissionTitle, invalidColor)
            changeBorderColorOfEditText(binding.dateOfAdmission, invalidColor)
            return null
        }else{
            changeTextColorOfTextView(binding.dateOfAdmissionTitle, validColor)
            changeBorderColorOfEditText(binding.dateOfAdmission, validColor)
        }

        val toa = binding.timeOfAdmission.text.toString()
        if (!Validation.validateTime(toa)) {
            changeTextColorOfTextView(binding.timeOfAdmissionTitle, invalidColor)
            changeBorderColorOfEditText(binding.timeOfAdmission, invalidColor)
            return null
        }else{
            changeTextColorOfTextView(binding.timeOfAdmissionTitle, validColor)
            changeBorderColorOfEditText(binding.timeOfAdmission, validColor)
        }

        val dod = binding.dateOfDischarge.text.toString()
        if (!Validation.validateDate(dod)) {
            changeTextColorOfTextView(binding.dateOfDischargeTitle, invalidColor)
            changeBorderColorOfEditText(binding.dateOfDischarge, invalidColor)
            return null
        }else{
            changeTextColorOfTextView(binding.dateOfDischargeTitle, validColor)
            changeBorderColorOfEditText(binding.dateOfDischarge, validColor)
        }

        val tod = binding.timeOfDischarge.text.toString()
        if (!Validation.validateTime(toa)) {
            changeTextColorOfTextView(binding.timeOfDischargeTitle, invalidColor)
            changeBorderColorOfEditText(binding.timeOfDischarge, invalidColor)
            return null
        }else{
            changeTextColorOfTextView(binding.timeOfDischargeTitle, validColor)
            changeBorderColorOfEditText(binding.timeOfDischarge, validColor)
        }

        val diagnosis = binding.diagnosis.text.toString()
        if (diagnosis.isEmpty()) {
            changeTextColorOfTextView(binding.diagnosisTitle, invalidColor)
            changeBorderColorOfEditText(binding.diagnosis, invalidColor)
            return null
        }else{
            changeTextColorOfTextView(binding.diagnosisTitle, validColor)
            changeBorderColorOfEditText(binding.diagnosis, validColor)
        }

        val patientHistory = binding.patientHistory.text.toString()
        if (patientHistory.isEmpty()) {
            changeTextColorOfTextView(binding.patientHistoryTitle, invalidColor)
            changeBorderColorOfEditText(binding.patientHistory, invalidColor)
            return null
        }else{
            changeTextColorOfTextView(binding.patientHistoryTitle, validColor)
            changeBorderColorOfEditText(binding.patientHistory, validColor)
        }

        val pastHistory = binding.pastHistory.text.toString()
        if (pastHistory.isEmpty()) {
            changeTextColorOfTextView(binding.pastHistoryTitle, invalidColor)
            changeBorderColorOfEditText(binding.pastHistory, invalidColor)
            return null
        }else{
            changeTextColorOfTextView(binding.pastHistoryTitle, validColor)
            changeBorderColorOfEditText(binding.pastHistory, validColor)
        }

        val familyHistory = binding.familyHistory.text.toString()
        if (familyHistory.isEmpty()) {
            changeTextColorOfTextView(binding.familyHistoryTitle, invalidColor)
            changeBorderColorOfEditText(binding.familyHistory, invalidColor)
            return null
        }else{
            changeTextColorOfTextView(binding.familyHistoryTitle, validColor)
            changeBorderColorOfEditText(binding.familyHistory, validColor)
        }

        val courseViewHolders = getMedicineTemplateHolders(binding.courseList)
        val courseList = mutableListOf<String>()
        for(medicineTemplateHolder in courseViewHolders){
            val itemBinding = medicineTemplateHolder.listItemLayoutBinding
            val courseItem = itemBinding.template.text.toString()
            if (courseItem.isEmpty()) {
                changeBorderColorOfEditText(itemBinding.template, invalidColor)
                return null
            }else{
                changeBorderColorOfEditText(itemBinding.template, validColor)
            }
            courseList.add(itemBinding.template.text.toString())
        }

        val investigations = binding.investigations.text.toString()
        if (investigations.isEmpty()) {
            changeTextColorOfTextView(binding.investigationsTitle, invalidColor)
            changeBorderColorOfEditText(binding.investigations, invalidColor)
            return null
        }else{
            changeTextColorOfTextView(binding.investigationsTitle, validColor)
            changeBorderColorOfEditText(binding.investigations, validColor)
        }

        val medicationViewHolders = getMedicineTemplateHolders(binding.medicationList)
        val medicationList = mutableListOf<String>()
        for(medicineTemplateHolder in medicationViewHolders){
            val itemBinding = medicineTemplateHolder.listItemLayoutBinding
            val courseItem = itemBinding.template.text.toString()
            if (courseItem.isEmpty()) {
                changeBorderColorOfEditText(itemBinding.template, invalidColor)
                return null
            }else{
                changeBorderColorOfEditText(itemBinding.template, validColor)
            }
            medicationList.add(itemBinding.template.text.toString())
        }

        val adviceViewHolders = getMedicineTemplateHolders(binding.adviceList)
        val adviceList = mutableListOf<String>()
        for(medicineTemplateHolder in adviceViewHolders){
            val itemBinding = medicineTemplateHolder.listItemLayoutBinding
            val courseItem = itemBinding.template.text.toString()
            if (courseItem.isEmpty()) {
                changeBorderColorOfEditText(itemBinding.template, invalidColor)
                return null
            }else{
                changeBorderColorOfEditText(itemBinding.template, validColor)
            }
            adviceList.add(itemBinding.template.text.toString())
        }
        val dischargeCard = DischargeCard(
            patientId,
            ipdNumber,
            StringUtils.formatName(firstName),
            StringUtils.formatName(middleName),
            StringUtils.formatName(lastName),
            gender,
            contactNumber,
            weight,
            height,
            0f,
            age,
            DateTimeUtils.getLocalDateTimeFromDate(dob),
            DateTimeUtils.getLocalDateTime(doa, toa)!!,
            DateTimeUtils.getLocalDateTime(dod, tod)!!,
            address, diagnosis, patientHistory, pastHistory, familyHistory,
            courseList, investigations, medicationList, adviceList
        )
        return dischargeCard
    }

    private fun getMedicineTemplateHolders(recyclerView: RecyclerView): List<MedicineTemplateListAdapter.MedicineTemplateHolder>{
        val list = mutableListOf<MedicineTemplateListAdapter.MedicineTemplateHolder>()
        var medicineTemplateHolder : MedicineTemplateListAdapter.MedicineTemplateHolder
        for(i in 0 until recyclerView.childCount){
            medicineTemplateHolder = recyclerView.getChildViewHolder(recyclerView.getChildAt(i)) as MedicineTemplateListAdapter.MedicineTemplateHolder
            list.add(medicineTemplateHolder)
        }
        return list
    }

    private fun changeBorderColorOfEditText(fieldInput: EditText, color: Int){
        val colorValue = ContextCompat.getColor(activity as Context, color)
        val fieldInputDrawable = fieldInput.background as StateListDrawable
        val dcs = fieldInputDrawable.constantState as DrawableContainer.DrawableContainerState
        val drawableItem = dcs.children[0] as GradientDrawable
        val pixels = R.dimen.login_edittext_background_stroke_width * resources.displayMetrics.density.toInt()
        drawableItem.setStroke(pixels, colorValue)
    }

    private fun changeBorderColorOfPrefix(textView: TextView, color: Int){
        val colorValue = ContextCompat.getColor(activity as Context, color)
        val fieldInputDrawable = textView.background as LayerDrawable
        val layerDrawable = fieldInputDrawable.findDrawableByLayerId(R.id.item)
        val drawableItem = layerDrawable as GradientDrawable
        val pixels = R.dimen.login_edittext_background_stroke_width * resources.displayMetrics.density.toInt()
        drawableItem.setStroke(pixels, colorValue)
    }

    private fun changeBorderColorOfSuffix(editText: EditText, color: Int){
        val colorValue = ContextCompat.getColor(activity as Context, color)
        val fieldInputDrawable = editText.background as LayerDrawable
        val layerDrawable = fieldInputDrawable.findDrawableByLayerId(R.id.item)
        val drawableItem = layerDrawable as GradientDrawable
        val pixels = R.dimen.login_edittext_background_stroke_width * resources.displayMetrics.density.toInt()
        drawableItem.setStroke(pixels, colorValue)
    }

    private fun changeTextColorOfTextView(textView: TextView, color: Int){
        val colorValue = ContextCompat.getColor(activity as Context, color)
        textView.setTextColor(colorValue)
    }


    private fun popTemplateMenu(adapter: RecyclerView.Adapter<out RecyclerView.ViewHolder>){
        bottomSheetDialog = context?.let { it -> BottomSheetDialog(it) }
        bottomSheetDialog?.setContentView(R.layout.bottom_sheet_layout)
        val recyclerView = bottomSheetDialog?.findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView?.adapter = adapter
        bottomSheetDialog?.show()
    }

    override fun onItemClicked(data: Any?, requester: String) {
        if(data is PatientHistoryTemplate){
            binding.patientHistory.setText(data.templateData)
            bottomSheetDialog?.cancel()
        }
        else if(data is MedicineTemplate && requester == COURSE_MEDICINE_TEMPLATE_REQUESTER){
            val templates = (binding.courseList.adapter as MedicineTemplateListAdapter).templates
            templates.add(data)
            (binding.courseList.adapter as MedicineTemplateListAdapter).notifyItemInserted(templates.size-1)
            bottomSheetDialog?.cancel()
        }
        else if(data is MedicineTemplate && requester == MEDICATION_MEDICINE_TEMPLATE_REQUESTER){
            val templates = (binding.medicationList.adapter as MedicineTemplateListAdapter).templates
            templates.add(data)
            (binding.medicationList.adapter as MedicineTemplateListAdapter).notifyItemInserted(templates.size-1)
            bottomSheetDialog?.cancel()
        }
    }

    fun startProgressBar(isVisible: Boolean){
        binding.root.isClickable = !isVisible
        (activity as MainActivity).startProgressBar(isVisible)
    }

}