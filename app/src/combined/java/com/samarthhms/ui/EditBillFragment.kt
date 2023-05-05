package com.samarthhms.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.drawable.DrawableContainer
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.StateListDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.samarthhms.R
import com.samarthhms.constants.Gender
import com.samarthhms.databinding.FragmentEditBillBinding
import com.samarthhms.domain.Status
import com.samarthhms.models.Bill
import com.samarthhms.models.BillItem
import com.samarthhms.utils.DateTimeUtils
import com.samarthhms.utils.StringUtils
import com.samarthhms.utils.Validation
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class EditBillFragment : Fragment(), OnUpdateBillSumListener {

    private val viewModel: GenerateBillViewModel by viewModels()

    private lateinit var binding: FragmentEditBillBinding

    private var billTotal: Int = 0

    private var previousBillNumber: String = ""

    private var bill: Bill? = null

    private fun initializeData(bill: Bill){
        previousBillNumber = bill.billNumber
        binding.billNumber.setText(StringUtils.formatYearWiseIdGeneral(bill.billNumber))
        binding.firstName.setText(bill.firstName)
        binding.middleName.setText(bill.middleName)
        binding.lastName.setText(bill.lastName)
        if(bill.gender == Gender.MALE){
            binding.genderMaleRadioGroupButton.isChecked = true
        }
        else{
            binding.genderFemaleRadioGroupButton.isChecked = true
        }
        binding.age.setText(bill.age)
        binding.contactNumber.setText(bill.contactNumber)
        binding.dateOfAdmission.setText(DateTimeUtils.getDate(bill.dateOfAdmission))
        binding.dateOfDischarge.setText(DateTimeUtils.getDate(bill.dateOfDischarge))
        binding.address.setText(bill.address)
        binding.diagnosis.setText(bill.diagnosis)

        binding.treatmentCharges.adapter = BillAdapter(this, bill.treatmentCharges.toMutableList())
        binding.managementCharges.adapter = BillAdapter(this, bill.managementCharges.toMutableList())

        binding.otherCharges.itemName.setText(bill.otherCharges.itemName)
        binding.otherCharges.rate.setText(bill.otherCharges.rate.toString())
        binding.otherCharges.quantity.setText(bill.otherCharges.quantity.toString())
        binding.otherCharges.sum.setText((bill.otherCharges.rate*bill.otherCharges.quantity).toString())

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditBillBinding.inflate(layoutInflater, container, false)

        val bill = EditBillFragmentArgs.fromBundle(requireArguments()).bill
        initializeData(bill)

        binding.addTreatmentChargesButton.setOnClickListener{
            val adapter = binding.treatmentCharges.adapter as BillAdapter
            val position = adapter.billItems.size
            adapter.billItems.add(BillItem("DEFAULT"))
            adapter.notifyItemInserted(position)
        }

        binding.addManagementChargesButton.setOnClickListener{
            val adapter = binding.managementCharges.adapter as BillAdapter
            val position = adapter.billItems.size
            adapter.billItems.add(BillItem("DEFAULT"))
            adapter.notifyItemInserted(position)
        }

        binding.otherCharges.deleteButton.visibility = View.GONE
        binding.otherCharges.rate.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged( s: CharSequence?, start: Int, count: Int, after: Int) {
//                    TODO("Not yet implemented")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                    TODO("Not yet implemented")
            }

            override fun afterTextChanged(s: Editable?) {
                val rate = if(binding.otherCharges.rate.text.isBlank()) 0 else binding.otherCharges.rate.text.toString().toInt()
                val quantity = if(binding.otherCharges.quantity.text.isBlank()) 0 else binding.otherCharges.quantity.text.toString().toInt()
                val previousSum = if(binding.otherCharges.sum.text.isBlank()) 0 else binding.otherCharges.sum.text.toString().toInt()
                val sum = rate * quantity
                binding.otherCharges.sum.setText(sum.toString())
                update(sum-previousSum)
            }
        })

        binding.otherCharges.quantity.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged( s: CharSequence?, start: Int, count: Int, after: Int) {
//                    TODO("Not yet implemented")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                    TODO("Not yet implemented")
            }

            override fun afterTextChanged(s: Editable?) {
                val rate = if(binding.otherCharges.rate.text.isBlank()) 0 else binding.otherCharges.rate.text.toString().toInt()
                val quantity = if(binding.otherCharges.quantity.text.isBlank()) 0 else binding.otherCharges.quantity.text.toString().toInt()
                val previousSum = if(binding.otherCharges.sum.text.isBlank()) 0 else binding.otherCharges.sum.text.toString().toInt()
                val sum = rate * quantity
                binding.otherCharges.sum.setText(sum.toString())
                update(sum-previousSum)
            }
        })

        binding.generateBillButton.setOnClickListener{
            startProgressBar(true)
            this.bill = getBill()
            if(Objects.nonNull(this.bill)){
                viewModel.saveBill(previousBillNumber, this.bill!!)
            }else{
                startProgressBar(false)
            }
        }

        viewModel.getBillStatus.observe(viewLifecycleOwner){
            if(it == Status.NONE){
                startProgressBar(false)
                return@observe
            }
            if(PackageManager.PERMISSION_GRANTED != context?.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ||
                PackageManager.PERMISSION_GRANTED != context?.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                return@observe
            }
            if(it == Status.SUCCESS && viewModel.billFile.value != null){
                startProgressBar(false)
                val action = EditBillFragmentDirections.actionEditBillFragmentToPdfDetailsFragment(viewModel.billFile.value!!)
                findNavController().navigate(action)
            }
        }

        viewModel.saveBillStatus.observe(viewLifecycleOwner){
            if(it == Status.NONE){
                startProgressBar(false)
                return@observe
            }
            if(it == Status.SUCCESS){
                startProgressBar(false)
                viewModel.makeBill(this.bill!!)
            }
        }

        return binding.root
    }

    private fun getBill(): Bill? {
        val invalidColor = R.color.red
        val validColor = R.color.blue_theme

//        val patientId = IdUtils.PATIENT_ID_PREFIX + binding.patientId.text
//        if (!Validation.validatePatientId(patientId)) {
//            Toast.makeText(activity, "Invalid Patient Id", Toast.LENGTH_SHORT).show()
//            changeTextColorOfTextView(binding.patientIdTitle, invalidColor)
//            changeBorderColorOfPrefix(binding.patientIdPrefix, invalidColor)
//            changeBorderColorOfSuffix(binding.patientId, invalidColor)
//            return null
//        }else{
//            changeTextColorOfTextView(binding.patientIdTitle, validColor)
//            changeBorderColorOfPrefix(binding.patientIdPrefix, validColor)
//            changeBorderColorOfSuffix(binding.patientId, validColor)
//        }

        val billNumber = binding.billNumber.text.toString()
        if (!Validation.validateIpdNumber(billNumber)) {

            changeTextColorOfTextView(binding.billNumberTitle, invalidColor)
            changeBorderColorOfEditText(binding.billNumber, invalidColor)
            return null
        } else {
            changeTextColorOfTextView(binding.billNumberTitle, validColor)
            changeBorderColorOfEditText(binding.billNumber, validColor)
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

        var billItemHolders = getBillItemHolders(binding.treatmentCharges)
        val treatmentChargesList = mutableListOf<BillItem>()
        for (billItemHolder in billItemHolders) {
            val itemBinding = billItemHolder.billItemLayoutBinding
            val itemName = itemBinding.itemName.text.toString()
            if (itemName.isBlank()) {
                Toast.makeText(activity, "Invalid Item Name", Toast.LENGTH_SHORT).show()
                changeTextColorOfTextView(itemBinding.itemNameTitle, invalidColor)
                changeBorderColorOfEditText(itemBinding.itemName, invalidColor)
                return null
            } else {
                changeTextColorOfTextView(itemBinding.itemNameTitle, validColor)
                changeBorderColorOfEditText(itemBinding.itemName, validColor)
            }
            val rate =
                if (itemBinding.rate.text.isBlank()) 0 else itemBinding.rate.text.toString().toInt()
            val quantity =
                if (itemBinding.quantity.text.isBlank()) 0 else itemBinding.quantity.text.toString()
                    .toInt()
            val billItem = BillItem(itemName, rate, quantity)
            treatmentChargesList.add(billItem)
        }

        billItemHolders = getBillItemHolders(binding.managementCharges)
        val managementChargesList = mutableListOf<BillItem>()
        for (billItemHolder in billItemHolders) {
            val itemBinding = billItemHolder.billItemLayoutBinding
            val itemName = itemBinding.itemName.text.toString()
            if (itemName.isBlank()) {
                Toast.makeText(activity, "Invalid Item Name", Toast.LENGTH_SHORT).show()
                changeTextColorOfTextView(itemBinding.itemNameTitle, invalidColor)
                changeBorderColorOfEditText(itemBinding.itemName, invalidColor)
                return null
            } else {
                changeTextColorOfTextView(itemBinding.itemNameTitle, validColor)
                changeBorderColorOfEditText(itemBinding.itemName, validColor)
            }
            val rate =
                if (itemBinding.rate.text.isBlank()) 0 else itemBinding.rate.text.toString().toInt()
            val quantity =
                if (itemBinding.quantity.text.isBlank()) 0 else itemBinding.quantity.text.toString()
                    .toInt()
            val billItem = BillItem(itemName, rate, quantity)
            managementChargesList.add(billItem)
        }


        val itemBinding = binding.otherCharges
        val itemName = itemBinding.itemName.text.toString()
        if (itemName.isBlank()) {
            Toast.makeText(activity, "Invalid Item Name", Toast.LENGTH_SHORT).show()
            changeTextColorOfTextView(itemBinding.itemNameTitle, invalidColor)
            changeBorderColorOfEditText(itemBinding.itemName, invalidColor)
            return null
        } else {
            changeTextColorOfTextView(itemBinding.itemNameTitle, validColor)
            changeBorderColorOfEditText(itemBinding.itemName, validColor)
        }
        val rate =
            if (itemBinding.rate.text.isBlank()) 0 else itemBinding.rate.text.toString().toInt()
        val quantity =
            if (itemBinding.quantity.text.isBlank()) 0 else itemBinding.quantity.text.toString()
                .toInt()
        val otherCharges = BillItem(itemName, rate, quantity)

        return Bill(
//            patientId,
            "",
            "",
            "",
            StringUtils.formatYearWiseId(billNumber),
            StringUtils.formatName(firstName),
            StringUtils.formatName(middleName),
            StringUtils.formatName(lastName),
            gender,
            age,
            contactNumber,
            DateTimeUtils.getLocalDateTimeFromDate(doa),
            DateTimeUtils.getLocalDateTimeFromDate(dod),
            address,
            diagnosis,
            treatmentChargesList,
            managementChargesList,
            otherCharges,
            billTotal
        )
    }

    private fun getBillItemHolders(recyclerView: RecyclerView): List<BillAdapter.BillItemHolder>{
        val list = mutableListOf<BillAdapter.BillItemHolder>()
        var billItemHolder : BillAdapter.BillItemHolder
        for(i in 0 until recyclerView.childCount){
            billItemHolder = recyclerView.getChildViewHolder(recyclerView.getChildAt(i)) as BillAdapter.BillItemHolder
            list.add(billItemHolder)
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

    @SuppressLint("SetTextI18n")
    override fun update(sum: Int){
        billTotal+=sum
        binding.billTotal.text = "Rs. "+StringUtils.formatPrice(billTotal)
    }

    fun startProgressBar(isVisible: Boolean){
        binding.root.isClickable = !isVisible
        (activity as MainActivity).startProgressBar(isVisible)
    }

//    fun getTotal(billItems: List<BillItem>):Int{
//        var sum = 0
//        billItems.forEach{ sum+=(it.rate * it.quantity) }
//        return sum
//    }
}