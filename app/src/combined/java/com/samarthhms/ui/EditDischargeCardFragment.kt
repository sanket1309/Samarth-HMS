package com.samarthhms.ui

import android.content.Context
import android.content.Intent
import android.graphics.drawable.DrawableContainer
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.StateListDrawable
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Filterable
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.samarthhms.R
import com.samarthhms.constants.Gender
import com.samarthhms.databinding.FragmentEditDischargeCardBinding
import com.samarthhms.domain.Status
import com.samarthhms.models.DischargeCard
import com.samarthhms.models.DischargeCardTemplate
import com.samarthhms.models.MedicineTemplate
import com.samarthhms.models.PatientHistoryTemplate
import com.samarthhms.utils.DateTimeUtils
import com.samarthhms.utils.StringUtils
import com.samarthhms.utils.Validation
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDateTime
import java.util.*


@AndroidEntryPoint
class EditDischargeCardFragment : Fragment(), RecyclerOnItemViewClickListener {

    private val viewModel: GenerateDischargeCardViewModel by viewModels()

    private lateinit var binding: FragmentEditDischargeCardBinding

    private var bottomSheetDialog: BottomSheetDialog? = null

    private var dischargeCardTemplate: DischargeCardTemplate? = null

    private var courseMedicineTemplateRequester = "courseMedicineTemplateRequester"

    private var medicationMedicineTemplateRequester = "medicationMedicineTemplateRequester"

    private var previousIpdNumber: String = ""

    private var dischargeCard: DischargeCard? = null

    private fun initializeData(dischargeCard: DischargeCard){
        previousIpdNumber = dischargeCard.ipdNumber
        binding.ipdNumber.setText(StringUtils.formatYearWiseIdGeneral(dischargeCard.ipdNumber))
        binding.firstName.setText(dischargeCard.firstName)
        binding.middleName.setText(dischargeCard.middleName)
        binding.lastName.setText(dischargeCard.lastName)
        if(dischargeCard.gender == Gender.MALE){
            binding.genderMaleRadioGroupButton.isChecked = true
        }
        else{
            binding.genderFemaleRadioGroupButton.isChecked = true
        }
        binding.age.setText(dischargeCard.ageFormat)

        binding.weight.setText(dischargeCard.weight.toString())
        binding.contactNumber.setText(dischargeCard.contactNumber)
        binding.dateOfAdmission.setText(DateTimeUtils.getDate(dischargeCard.dateOfAdmission))
        binding.timeOfAdmission.setText(DateTimeUtils.getTime(dischargeCard.dateOfAdmission))
        binding.dateOfDischarge.setText(DateTimeUtils.getDate(dischargeCard.dateOfDischarge))
        binding.timeOfDischarge.setText(DateTimeUtils.getTime(dischargeCard.dateOfDischarge))
        binding.address.setText(dischargeCard.address)
        binding.diagnosis.setText(dischargeCard.diagnosis)
        binding.patientHistory.setText(dischargeCard.patientHistory)
        binding.pastHistory.setText(dischargeCard.pastHistory)
        binding.familyHistory.setText(dischargeCard.familyHistory)
        binding.investigations.setText(dischargeCard.investigations)

        val courseList = mutableListOf<MedicineTemplate>()
        for(templateData in dischargeCard.course){
            courseList.add(MedicineTemplate("",templateData))
        }
        binding.courseList.adapter = MedicineTemplateListAdapter(courseList)

        val medicationList = mutableListOf<MedicineTemplate>()
        for(templateData in dischargeCard.medicationsOnDischarge){
            medicationList.add(MedicineTemplate("",templateData))
        }
        binding.medicationList.adapter = MedicineTemplateListAdapter(medicationList)

        val adviceList = mutableListOf<MedicineTemplate>()
        for(templateData in dischargeCard.advice){
            adviceList.add(MedicineTemplate("",templateData))
        }
        binding.adviceList.adapter = MedicineTemplateListAdapter(adviceList)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditDischargeCardBinding.inflate(layoutInflater, container, false)

        startProgressBar(true)
        viewModel.getData()

        val dischargeCard = EditDischargeCardFragmentArgs.fromBundle(requireArguments()).dischargeCard
        initializeData(dischargeCard)

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
                popTemplateMenu(adapter,adapter)
            }
        }

        binding.addCourseButton.setOnClickListener{
            if(dischargeCardTemplate!=null){
                val adapter = MedicineTemplateSearchAdapter(this, dischargeCardTemplate!!.medicineTemplates, courseMedicineTemplateRequester)
                popTemplateMenu(adapter,adapter)
            }
        }

        binding.addMedicationButton.setOnClickListener{
            if(dischargeCardTemplate!=null){
                val adapter = MedicineTemplateSearchAdapter(this, dischargeCardTemplate!!.medicineTemplates, medicationMedicineTemplateRequester)
                popTemplateMenu(adapter,adapter)
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
            this.dischargeCard = getDischargeCard()
            if(Objects.nonNull(this.dischargeCard)){
                viewModel.saveDischargeCard(previousIpdNumber, this.dischargeCard!!)
            }else{
                startProgressBar(false)
            }
        }

//        ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE), 2)

        viewModel.getDischargeCardStatus.observe(viewLifecycleOwner){
            if(it == Status.NONE){
                startProgressBar(false)
                return@observe
            }
//            if(PackageManager.PERMISSION_GRANTED != context?.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ||
//                PackageManager.PERMISSION_GRANTED != context?.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)){
//                return@observe
//            }
            if(it == Status.SUCCESS && viewModel.dischargeCardFile.value != null){
                startProgressBar(false)
                val action = EditDischargeCardFragmentDirections.actionEditDischargeCardFragmentToPdfDetailsFragment(viewModel.dischargeCardFile.value!!)
                findNavController().navigate(action)
            }
        }

        viewModel.saveDischargeCardStatus.observe(viewLifecycleOwner){
            if(it == Status.NONE){
                startProgressBar(false)
                return@observe
            }
            if(it == Status.SUCCESS){
                Toast.makeText(context, "Saved Successfully",Toast.LENGTH_SHORT)
                startProgressBar(false)
                try{
                    if(Environment.isExternalStorageManager()){
                        viewModel.makeDischargeCard(this.dischargeCard!!)
                    }else{
                        startActivityForResult(Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION),0)
                    }

                }catch (e: Exception){
                    Toast.makeText(context, "Something Went Wrong",Toast.LENGTH_SHORT)
                }
            }
        }
        return binding.root
    }

    private fun getDischargeCard(): DischargeCard? {
        val invalidColor = R.color.red
        val validColor = R.color.blue_theme

//        val patientId = IdUtils.PATIENT_ID_PREFIX + binding.patientId.text
//        if (!Validation.validatePatientId(patientId)) {
//            Toast.makeText(activity, "Invalid Patient Id",Toast.LENGTH_SHORT).show()
//            changeTextColorOfTextView(binding.patientIdTitle, invalidColor)
//            changeBorderColorOfPrefix(binding.patientIdPrefix, invalidColor)
//            changeBorderColorOfSuffix(binding.patientId, invalidColor)
//            return null
//        }else{
//            changeTextColorOfTextView(binding.patientIdTitle, validColor)
//            changeBorderColorOfPrefix(binding.patientIdPrefix, validColor)
//            changeBorderColorOfSuffix(binding.patientId, validColor)
//        }

        val ipdNumber = binding.ipdNumber.text.toString()
        if (!Validation.validateIpdNumber(ipdNumber)) {
            Toast.makeText(activity, "Invalid IPD Number", Toast.LENGTH_SHORT).show()
            changeTextColorOfTextView(binding.ipdNumberTitle, invalidColor)
            changeBorderColorOfEditText(binding.ipdNumber, invalidColor)
            return null
        } else {
            changeTextColorOfTextView(binding.ipdNumberTitle, validColor)
            changeBorderColorOfEditText(binding.ipdNumber, validColor)
        }

        val firstName = binding.firstName.text.toString()
        if (!Validation.validateName(firstName)) {
            Toast.makeText(activity, "Invalid First Name", Toast.LENGTH_SHORT).show()
            changeTextColorOfTextView(binding.firstNameTitle, invalidColor)
            changeBorderColorOfEditText(binding.firstName, invalidColor)
            return null
        } else {
            changeTextColorOfTextView(binding.firstNameTitle, validColor)
            changeBorderColorOfEditText(binding.firstName, validColor)
        }

        val middleName = binding.middleName.text.toString().trim()
        if (!Validation.validateName(middleName)) {
            Toast.makeText(activity, "Invalid Middle Name", Toast.LENGTH_SHORT).show()
            changeTextColorOfTextView(binding.middleNameTitle, invalidColor)
            changeBorderColorOfEditText(binding.middleName, invalidColor)
            return null
        } else {
            changeTextColorOfTextView(binding.middleNameTitle, validColor)
            changeBorderColorOfEditText(binding.middleName, validColor)
        }

        val lastName = binding.lastName.text.toString()
        if (!Validation.validateName(lastName)) {
            Toast.makeText(activity, "Invalid Last Name", Toast.LENGTH_SHORT).show()
            changeTextColorOfTextView(binding.lastNameTitle, invalidColor)
            changeBorderColorOfEditText(binding.lastName, invalidColor)
            return null
        } else {
            changeTextColorOfTextView(binding.lastNameTitle, validColor)
            changeBorderColorOfEditText(binding.lastName, validColor)
        }

        val gender =
            if (binding.genderMaleRadioGroupButton.isChecked) Gender.MALE else Gender.FEMALE

        val weightVal = binding.weight.text.toString()
        val weight = if (weightVal.isBlank()) 0f else weightVal.toFloat()
        if (weight <= 0f) {
            Toast.makeText(activity, "Invalid Weight", Toast.LENGTH_SHORT).show()
            changeTextColorOfTextView(binding.weightTitle, invalidColor)
            changeBorderColorOfEditText(binding.weight, invalidColor)
            return null
        } else {
            changeTextColorOfTextView(binding.weightTitle, validColor)
            changeBorderColorOfEditText(binding.weight, validColor)
        }
//
//        val heightVal = binding.height.text.toString()
//        val height = if(heightVal.isBlank()) 0f else heightVal.toFloat()
//        if (height <= 0f) {
//            Toast.makeText(activity, "Invalid Height",Toast.LENGTH_SHORT).show()
//            changeTextColorOfTextView(binding.heightTitle, invalidColor)
//            changeBorderColorOfEditText(binding.height, invalidColor)
//            return null
//        }else{
//            changeTextColorOfTextView(binding.heightTitle, validColor)
//            changeBorderColorOfEditText(binding.height, validColor)
//        }

        val age = binding.age.text.toString()
        if (age.isBlank()) {
            Toast.makeText(activity, "Invalid Age", Toast.LENGTH_SHORT).show()
            changeTextColorOfTextView(binding.ageTitle, invalidColor)
            changeBorderColorOfEditText(binding.age, invalidColor)
            return null
        } else {
            changeTextColorOfTextView(binding.ageTitle, validColor)
            changeBorderColorOfEditText(binding.age, validColor)
        }

//        val dob = binding.dateOfBirth.text.toString()
//        if (!Validation.validateDate(dob)) {
//            Toast.makeText(activity, "Invalid Date of Birth",Toast.LENGTH_SHORT).show()
//            changeTextColorOfTextView(binding.dateOfBirthTitle, invalidColor)
//            changeBorderColorOfEditText(binding.dateOfBirth, invalidColor)
//            return null
//        }else{
//            changeTextColorOfTextView(binding.dateOfBirthTitle, validColor)
//            changeBorderColorOfEditText(binding.dateOfBirth, validColor)
//        }

        val contactNumber = binding.contactNumber.text.toString().replace(" ", "")
        if (!Validation.validateContactNumber(contactNumber)) {
            Toast.makeText(activity, "Invalid Contact Number", Toast.LENGTH_SHORT).show()
            changeTextColorOfTextView(binding.contactNumberTitle, invalidColor)
            changeBorderColorOfEditText(binding.contactNumber, invalidColor)
            return null
        } else {
            changeTextColorOfTextView(binding.contactNumberTitle, validColor)
            changeBorderColorOfEditText(binding.contactNumber, validColor)
        }

        val address = binding.address.text.toString()
        if (address.isBlank()) {
            Toast.makeText(activity, "Invalid Address", Toast.LENGTH_SHORT).show()
            changeTextColorOfTextView(binding.addressTitle, invalidColor)
            changeBorderColorOfEditText(binding.address, invalidColor)
            return null
        } else {
            changeTextColorOfTextView(binding.addressTitle, validColor)
            changeBorderColorOfEditText(binding.address, validColor)
        }

        val doa = binding.dateOfAdmission.text.toString()
        if (!Validation.validateDate(doa)) {
            Toast.makeText(activity, "Invalid Date of Admission", Toast.LENGTH_SHORT).show()
            changeTextColorOfTextView(binding.dateOfAdmissionTitle, invalidColor)
            changeBorderColorOfEditText(binding.dateOfAdmission, invalidColor)
            return null
        } else {
            changeTextColorOfTextView(binding.dateOfAdmissionTitle, validColor)
            changeBorderColorOfEditText(binding.dateOfAdmission, validColor)
        }

        val toa = binding.timeOfAdmission.text.toString()
        if (!Validation.validateTime(toa)) {
            Toast.makeText(activity, "Invalid Time of Admission", Toast.LENGTH_SHORT).show()
            changeTextColorOfTextView(binding.timeOfAdmissionTitle, invalidColor)
            changeBorderColorOfEditText(binding.timeOfAdmission, invalidColor)
            return null
        } else {
            changeTextColorOfTextView(binding.timeOfAdmissionTitle, validColor)
            changeBorderColorOfEditText(binding.timeOfAdmission, validColor)
        }

        val dod = binding.dateOfDischarge.text.toString()
        if (!Validation.validateDate(dod)) {
            Toast.makeText(activity, "Invalid Date of Discharge", Toast.LENGTH_SHORT).show()
            changeTextColorOfTextView(binding.dateOfDischargeTitle, invalidColor)
            changeBorderColorOfEditText(binding.dateOfDischarge, invalidColor)
            return null
        } else {
            changeTextColorOfTextView(binding.dateOfDischargeTitle, validColor)
            changeBorderColorOfEditText(binding.dateOfDischarge, validColor)
        }

        val tod = binding.timeOfDischarge.text.toString()
        if (!Validation.validateTime(toa)) {
            Toast.makeText(activity, "Invalid Time of Discharge", Toast.LENGTH_SHORT).show()
            changeTextColorOfTextView(binding.timeOfDischargeTitle, invalidColor)
            changeBorderColorOfEditText(binding.timeOfDischarge, invalidColor)
            return null
        } else {
            changeTextColorOfTextView(binding.timeOfDischargeTitle, validColor)
            changeBorderColorOfEditText(binding.timeOfDischarge, validColor)
        }

        val diagnosis = binding.diagnosis.text.toString()
        if (diagnosis.isBlank()) {
            Toast.makeText(activity, "Invalid Diagnosis", Toast.LENGTH_SHORT).show()
            changeTextColorOfTextView(binding.diagnosisTitle, invalidColor)
            changeBorderColorOfEditText(binding.diagnosis, invalidColor)
            return null
        } else {
            changeTextColorOfTextView(binding.diagnosisTitle, validColor)
            changeBorderColorOfEditText(binding.diagnosis, validColor)
        }

        val patientHistory = binding.patientHistory.text.toString()
        if (patientHistory.isBlank()) {
            Toast.makeText(activity, "Invalid Patient H/O", Toast.LENGTH_SHORT).show()
            changeTextColorOfTextView(binding.patientHistoryTitle, invalidColor)
            changeBorderColorOfEditText(binding.patientHistory, invalidColor)
            return null
        } else {
            changeTextColorOfTextView(binding.patientHistoryTitle, validColor)
            changeBorderColorOfEditText(binding.patientHistory, validColor)
        }

        val pastHistory = binding.pastHistory.text.toString()
        if (pastHistory.isBlank()) {
            Toast.makeText(activity, "Invalid Past History", Toast.LENGTH_SHORT).show()
            changeTextColorOfTextView(binding.pastHistoryTitle, invalidColor)
            changeBorderColorOfEditText(binding.pastHistory, invalidColor)
            return null
        } else {
            changeTextColorOfTextView(binding.pastHistoryTitle, validColor)
            changeBorderColorOfEditText(binding.pastHistory, validColor)
        }

        val familyHistory = binding.familyHistory.text.toString()
        if (familyHistory.isBlank()) {
            Toast.makeText(activity, "Invalid Family History", Toast.LENGTH_SHORT).show()
            changeTextColorOfTextView(binding.familyHistoryTitle, invalidColor)
            changeBorderColorOfEditText(binding.familyHistory, invalidColor)
            return null
        } else {
            changeTextColorOfTextView(binding.familyHistoryTitle, validColor)
            changeBorderColorOfEditText(binding.familyHistory, validColor)
        }

        val courseViewHolders = getMedicineTemplateHolders(binding.courseList)
        val courseList = mutableListOf<String>()
        for (medicineTemplateHolder in courseViewHolders) {
            val itemBinding = medicineTemplateHolder.listItemLayoutBinding
            val courseItem = itemBinding.template.text.toString()
            if (courseItem.isBlank()) {
                Toast.makeText(activity, "Invalid Item Name", Toast.LENGTH_SHORT).show()
                changeBorderColorOfEditText(itemBinding.template, invalidColor)
                return null
            } else {
                changeBorderColorOfEditText(itemBinding.template, validColor)
            }
            courseList.add(itemBinding.template.text.toString())
        }

        val investigations = binding.investigations.text.toString()
        if (investigations.isBlank()) {
            Toast.makeText(activity, "Invalid Investigations", Toast.LENGTH_SHORT).show()
            changeTextColorOfTextView(binding.investigationsTitle, invalidColor)
            changeBorderColorOfEditText(binding.investigations, invalidColor)
            return null
        } else {
            changeTextColorOfTextView(binding.investigationsTitle, validColor)
            changeBorderColorOfEditText(binding.investigations, validColor)
        }

        val medicationViewHolders = getMedicineTemplateHolders(binding.medicationList)
        val medicationList = mutableListOf<String>()
        for (medicineTemplateHolder in medicationViewHolders) {
            val itemBinding = medicineTemplateHolder.listItemLayoutBinding
            val courseItem = itemBinding.template.text.toString()
            if (courseItem.isBlank()) {
                Toast.makeText(activity, "Invalid Item Name", Toast.LENGTH_SHORT).show()
                changeBorderColorOfEditText(itemBinding.template, invalidColor)
                return null
            } else {
                changeBorderColorOfEditText(itemBinding.template, validColor)
            }
            medicationList.add(itemBinding.template.text.toString())
        }

        val adviceViewHolders = getMedicineTemplateHolders(binding.adviceList)
        val adviceList = mutableListOf<String>()
        for (medicineTemplateHolder in adviceViewHolders) {
            val itemBinding = medicineTemplateHolder.listItemLayoutBinding
            val courseItem = itemBinding.template.text.toString()
            if (courseItem.isBlank()) {
                Toast.makeText(activity, "Invalid Item Name", Toast.LENGTH_SHORT).show()
                changeBorderColorOfEditText(itemBinding.template, invalidColor)
                return null
            } else {
                changeBorderColorOfEditText(itemBinding.template, validColor)
            }
            adviceList.add(itemBinding.template.text.toString())
        }
        return DischargeCard(
//            patientId,
            "",
            StringUtils.formatYearWiseId(ipdNumber),
            StringUtils.formatName(firstName),
            StringUtils.formatName(middleName),
            StringUtils.formatName(lastName),
            gender,
            contactNumber,
            weight,
            0f,
            0f,
            age,
//            DateTimeUtils.getLocalDateTimeFromDate(dob),
            LocalDateTime.now(),
            DateTimeUtils.getLocalDateTime(doa, toa)!!,
            DateTimeUtils.getLocalDateTime(dod, tod)!!,
            address, diagnosis, patientHistory, pastHistory, familyHistory,
            courseList, investigations, medicationList, adviceList
        )
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

//    private fun changeBorderColorOfPrefix(textView: TextView, color: Int){
//        val colorValue = ContextCompat.getColor(activity as Context, color)
//        val fieldInputDrawable = textView.background as LayerDrawable
//        val layerDrawable = fieldInputDrawable.findDrawableByLayerId(R.id.item)
//        val drawableItem = layerDrawable as GradientDrawable
//        val pixels = R.dimen.login_edittext_background_stroke_width * resources.displayMetrics.density.toInt()
//        drawableItem.setStroke(pixels, colorValue)
//    }

//    private fun changeBorderColorOfSuffix(editText: EditText, color: Int){
//        val colorValue = ContextCompat.getColor(activity as Context, color)
//        val fieldInputDrawable = editText.background as LayerDrawable
//        val layerDrawable = fieldInputDrawable.findDrawableByLayerId(R.id.item)
//        val drawableItem = layerDrawable as GradientDrawable
//        val pixels = R.dimen.login_edittext_background_stroke_width * resources.displayMetrics.density.toInt()
//        drawableItem.setStroke(pixels, colorValue)
//    }

    private fun changeTextColorOfTextView(textView: TextView, color: Int){
        val colorValue = ContextCompat.getColor(activity as Context, color)
        textView.setTextColor(colorValue)
    }


    private fun popTemplateMenu(adapter: RecyclerView.Adapter<out RecyclerView.ViewHolder>, filterable: Filterable){
        bottomSheetDialog = context?.let { it -> BottomSheetDialog(it) }
        bottomSheetDialog?.setContentView(R.layout.bottom_sheet_layout)
        val recyclerView = bottomSheetDialog?.findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView?.adapter = adapter
        val searchView = bottomSheetDialog?.findViewById<SearchView>(R.id.search_view)
        searchView?.setOnQueryTextListener(
            object: SearchView.OnQueryTextListener {
                override fun onQueryTextChange(newText: String?): Boolean {
                    filterable.filter.filter(newText)
                    return false
                }

                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }
            }
        )
        searchView?.isFocusable = true
        searchView?.isIconified = false
        searchView?.requestFocusFromTouch()
        bottomSheetDialog?.show()
    }

    override fun onItemClicked(data: Any?, requester: String, isLongPress: Boolean) {
        if(data is PatientHistoryTemplate){
            binding.patientHistory.setText(data.templateData)
            bottomSheetDialog?.cancel()
        }
        else if(data is MedicineTemplate && requester == courseMedicineTemplateRequester){
            val templates = (binding.courseList.adapter as MedicineTemplateListAdapter).templates
            templates.add(data)
            (binding.courseList.adapter as MedicineTemplateListAdapter).notifyItemInserted(templates.size-1)
            bottomSheetDialog?.cancel()
        }
        else if(data is MedicineTemplate && requester == medicationMedicineTemplateRequester){
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