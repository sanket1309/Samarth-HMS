package com.samarthhms.ui

import android.content.Context
import android.graphics.drawable.DrawableContainer
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.StateListDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.getColor
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.samarthhms.R
import com.samarthhms.constants.Gender
import com.samarthhms.databinding.FragmentAddNewPatientBinding
import com.samarthhms.domain.Status
import com.samarthhms.models.Patient
import com.samarthhms.navigator.Navigator
import com.samarthhms.utils.DateTimeUtils
import com.samarthhms.utils.StringUtils
import com.samarthhms.utils.Validation
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AddNewPatientFragment : Fragment() {

    @Inject
    lateinit var navigator : Navigator

    private lateinit var binding: FragmentAddNewPatientBinding

    private val addNewPatientViewModel: AddNewPatientViewModel by viewModels()

    private lateinit var patientData: Patient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddNewPatientBinding.inflate(layoutInflater, container, false)
        binding.savePatientButton.setOnClickListener{
            val invalidColor = R.color.red
            val validColor = R.color.blue_theme

            val firstName = binding.firstName.text.toString()
            if (!Validation.validateName(firstName)){
                Toast.makeText(activity, "Invalid First Name",Toast.LENGTH_SHORT).show()
                changeColorOfInputFields(binding.firstNameTitle, binding.firstName, invalidColor)
                return@setOnClickListener
            }
            else{
                changeColorOfInputFields(binding.firstNameTitle, binding.firstName, validColor)
            }

            val middleName = binding.middleName.text.toString()
            if (!Validation.validateName(middleName)){
                Toast.makeText(activity, "Invalid Middle Name",Toast.LENGTH_SHORT).show()
                changeColorOfInputFields(binding.middleNameTitle, binding.middleName, invalidColor)
                return@setOnClickListener
            }
            else{
                changeColorOfInputFields(binding.middleNameTitle, binding.middleName, validColor)
            }

            val lastName = binding.lastName.text.toString()
            if (!Validation.validateName(lastName)){
                Toast.makeText(activity, "Invalid Last Name",Toast.LENGTH_SHORT).show()
                changeColorOfInputFields(binding.lastNameTitle, binding.lastName, invalidColor)
                return@setOnClickListener
            }
            else{
                changeColorOfInputFields(binding.lastNameTitle, binding.lastName, validColor)
            }

            val gender = if (binding.genderMaleRadioGroupButton.isChecked) Gender.MALE else Gender.FEMALE

            val contactNumber = binding.contactNumber.text.toString().replace(" ","")
            if (!Validation.validateContactNumber(contactNumber)){
                Toast.makeText(activity, "Invalid Contact Number",Toast.LENGTH_SHORT).show()
                changeColorOfInputFields(binding.contactNumberTitle, binding.contactNumber, invalidColor)
                return@setOnClickListener
            }
            else{
                changeColorOfInputFields(binding.contactNumberTitle, binding.contactNumber, validColor)
            }

            val dateOfBirth = binding.dateOfBirth.text.toString()
            if (!Validation.validateDate(dateOfBirth)){
                Toast.makeText(activity, "Invalid Date of Birth",Toast.LENGTH_SHORT).show()
                changeColorOfInputFields(binding.dateOfBirthTitle, binding.dateOfBirth, invalidColor)
                return@setOnClickListener
            }
            else{
                changeColorOfInputFields(binding.dateOfBirthTitle, binding.dateOfBirth, validColor)
            }

            val town = binding.town.text.toString()
            if (!Validation.validateName(town)){
                Toast.makeText(activity, "Invalid Town",Toast.LENGTH_SHORT).show()
                changeColorOfInputFields(binding.townTitle, binding.town, invalidColor)
                return@setOnClickListener
            }
            else{
                changeColorOfInputFields(binding.townTitle, binding.town, validColor)
            }

            val taluka = binding.taluka.text.toString()
            if (!Validation.validateName(taluka)){
                Toast.makeText(activity, "Invalid Taluka",Toast.LENGTH_SHORT).show()
                changeColorOfInputFields(binding.talukaTitle, binding.taluka, invalidColor)
                return@setOnClickListener
            }
            else{
                changeColorOfInputFields(binding.talukaTitle, binding.taluka, validColor)
            }

            val district = binding.district.text.toString()
            if (!Validation.validateName(district)){
                Toast.makeText(activity, "Invalid District",Toast.LENGTH_SHORT).show()
                changeColorOfInputFields(binding.districtTitle, binding.district, invalidColor)
                return@setOnClickListener
            }
            else{
                changeColorOfInputFields(binding.districtTitle, binding.district, validColor)
            }

            val patient = Patient(
                "",
                StringUtils.formatName(firstName),
                StringUtils.formatName(middleName),
                StringUtils.formatName(lastName),
                gender,
                contactNumber,
                DateTimeUtils.getLocalDateTimeFromDate(dateOfBirth),
                StringUtils.formatName(town),
                StringUtils.formatName(taluka),
                StringUtils.formatName(district)
            )
            patientData = patient
            addNewPatientViewModel.addPatient(patient)
        }

        addNewPatientViewModel.addPatientStatus.observe(viewLifecycleOwner){
            when(it){
                Status.SUCCESS -> onSuccess()
                Status.FAILURE -> onFailure()
                else -> {}
            }
        }

        return binding.root
    }

    private fun onSuccess(){
        Toast.makeText(activity, "Added Record Successfully", Toast.LENGTH_SHORT).show()
        val controller = findNavController()
        val action = AddNewPatientFragmentDirections.actionAddNewPatientFragmentToAddVisitFragment(patientData)
        controller.navigate(action)
    }

    private fun onFailure(){
        Toast.makeText(activity, "Something Went Wrong", Toast.LENGTH_SHORT).show()
    }

    private fun changeColorOfInputFields(fieldTitle: TextView, fieldInput: EditText, color: Int){
        val colorValue = getColor(activity as Context, color)
        fieldTitle.setTextColor(colorValue)
        val fieldInputDrawable = fieldInput.background as StateListDrawable
        val dcs = fieldInputDrawable.constantState as DrawableContainer.DrawableContainerState
        val drawableItem = dcs.children[0] as GradientDrawable
        val pixels = R.dimen.login_edittext_background_stroke_width * resources.displayMetrics.density.toInt()
        drawableItem.setStroke(pixels, colorValue)
    }

}