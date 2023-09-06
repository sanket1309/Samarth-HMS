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
import com.samarthhms.constants.Constants
import com.samarthhms.constants.Gender
import com.samarthhms.databinding.FragmentEditBillBinding
import com.samarthhms.databinding.FragmentGenerateBillBinding
import com.samarthhms.domain.Status
import com.samarthhms.models.Bill
import com.samarthhms.models.BillItem
import com.samarthhms.navigator.Navigator
import com.samarthhms.utils.*
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class EditBillFragment : Fragment(), OnUpdateBillSumListener {

    @Inject
    lateinit var navigator: Navigator

    private val viewModel: GenerateBillViewModel by viewModels()

    private lateinit var binding: FragmentEditBillBinding

    private var billTotal: Int = 0

    private var bill: Bill? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditBillBinding.inflate(layoutInflater, container, false)

        val bill = EditBillFragmentArgs.fromBundle(requireArguments()).bill
        UiDataDisplayUtils.displayBill(binding.root, bill)

        binding.addTreatmentChargesButton.setOnClickListener{ addBillItem(binding.treatmentCharges) }
        binding.addManagementChargesButton.setOnClickListener{ addBillItem(binding.managementCharges) }

        binding.otherCharges.rate.addTextChangedListener(TextChangeListenerUtils.getTextWatcher(onAfterTC = { UiDataDisplayUtils.updateBillItemSum(binding.otherCharges, this, false)}))
        binding.otherCharges.quantity.addTextChangedListener(TextChangeListenerUtils.getTextWatcher(onAfterTC = { UiDataDisplayUtils.updateBillItemSum(binding.otherCharges, this, false)}))
        binding.deleteBillButton.setOnClickListener{ onGenerateBill() }
        binding.saveBillButton.setOnClickListener{ onSaveBill() }
        binding.generateBillButton.setOnClickListener{ onGenerateBill() }

        viewModel.getBillStatus.observe(viewLifecycleOwner){ onGetBillStatus(it) }
        viewModel.saveBillStatus.observe(viewLifecycleOwner){ onSaveBillStatus(it)}

        return binding.root
    }

    private fun onSaveBillStatus(status:Status){
        if(status == Status.NONE){
            startProgressBar(false)
            return
        }

        if(status == Status.SUCCESS){
            ToastUtils.showToast(requireContext(), Constants.Messages.SAVED_RECORD_SUCCESSFULLY)
            startProgressBar(false)
        }
    }

    private fun onGetBillStatus(status: Status){
        if(status == Status.NONE){
            startProgressBar(false)
            return
        }
        if(status == Status.SUCCESS && viewModel.billFile.value != null){
            startProgressBar(false)
            navigator.navigateToFragment(this, GenerateBillFragmentDirections.actionGenerateBillFragmentToPdfDetailsFragment(viewModel.billFile.value!!))
        }
    }

    private fun onSaveBill(){
        ValidationUtils.validateBill(binding.root, requireContext(), resources)
        DialogUtils.popDialog(requireContext(), onPositive = {
            val bill = UiDataExtractorUtils.getBill(binding.root)
            if(Objects.nonNull(bill)){
                viewModel.saveBill(bill.billNumber, bill)
            }else{
                startProgressBar(false)
            }
        }, message = Constants.DialogMessages.SAVE_BILL,
        positiveMessage = Constants.DialogMessages.YES,
        negativeMessage = Constants.DialogMessages.NO)
    }

    private fun onGenerateBill(){
        startProgressBar(true)
        val permissionLauncher = PermissionUtils.getPermissionLauncher(this, {onGrantedPermission()}, {onDeniedPermission()})
        val permissionsToRequest = PermissionUtils.checkPermissions(requireContext(), Constants.Permissons.PERMISSION_LIST)
        if(permissionsToRequest.isEmpty()) onGrantedPermission()
        permissionLauncher.launch(permissionsToRequest.toTypedArray())
    }

    private fun onGrantedPermission(){
        viewModel.makeBill(bill!!)
    }

    private fun onDeniedPermission(){
        ToastUtils.showToast(requireContext(), Constants.Messages.PERMISSION_DENIED_GENERATE_BILL)
    }

    private fun addBillItem(recyclerView: RecyclerView){
        val adapter = recyclerView.adapter as BillAdapter
        adapter.appendData(BillItem())
    }

    override fun update(sum: Int){
        billTotal+=sum
        binding.billTotal.text = "Rs. "+StringUtils.formatPrice(billTotal)
    }

    fun startProgressBar(isVisible: Boolean){
        (activity as MainActivity).startProgressBar(isVisible)
    }
}