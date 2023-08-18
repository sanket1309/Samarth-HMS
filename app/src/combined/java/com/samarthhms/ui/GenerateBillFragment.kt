package com.samarthhms.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.samarthhms.constants.Constants
import com.samarthhms.databinding.FragmentGenerateBillBinding
import com.samarthhms.domain.Status
import com.samarthhms.models.Bill
import com.samarthhms.models.BillItem
import com.samarthhms.utils.*
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class GenerateBillFragment : Fragment(), OnUpdateBillSumListener {

    private val viewModel: GenerateBillViewModel by viewModels()

    private lateinit var binding: FragmentGenerateBillBinding

    private var billTotal: Int = 0

    private var bill: Bill? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGenerateBillBinding.inflate(layoutInflater, container, false)
        binding.treatmentCharges.adapter = BillAdapter(this, Constants.BillConstants.DEFAULT_TREATMENT_CHARGES.toMutableList())
        binding.managementCharges.adapter = BillAdapter(this, Constants.BillConstants.DEFAULT_MANAGEMENT_CHARGES.toMutableList())
        binding.otherCharges.itemName.setText(Constants.BillConstants.OTHER_CHARGES_NAME)

        binding.addTreatmentChargesButton.setOnClickListener{ addBillItem(binding.treatmentCharges) }
        binding.addManagementChargesButton.setOnClickListener{ addBillItem(binding.managementCharges) }

        binding.otherCharges.rate.addTextChangedListener(TextChangeListenerUtils.getTextWatcher(onAfterTC = {UiDataDisplayUtils.updateBillItemSum(binding.otherCharges, this, false)}))
        binding.otherCharges.quantity.addTextChangedListener(TextChangeListenerUtils.getTextWatcher(onAfterTC = {UiDataDisplayUtils.updateBillItemSum(binding.otherCharges, this, false)}))
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
            startProgressBar(false)
            viewModel.makeBill(bill!!)
        }
    }

    private fun onGetBillStatus(status: Status){
        if(status == Status.NONE){
            startProgressBar(false)
            return
        }
        if(PackageManager.PERMISSION_GRANTED != context?.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ||
            PackageManager.PERMISSION_GRANTED != context?.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            return
        }
        if(status == Status.SUCCESS && viewModel.billFile.value != null){
            startProgressBar(false)
            val action = GenerateBillFragmentDirections.actionGenerateBillFragmentToPdfDetailsFragment(viewModel.billFile.value!!)
            findNavController().navigate(action)
        }
    }

    private fun onGenerateBill(){
        startProgressBar(true)
        val permissionLauncher = PermissionUtils.getPermissionLauncher(this, {onGrantedPermission()}, {onDeniedPermission()})
        val permissionsToRequest = PermissionUtils.checkPermissions(requireContext(), Constants.Permissons.PERMISSION_LIST)
        if(permissionsToRequest.isEmpty()) onGrantedPermission()
        permissionLauncher.launch(permissionsToRequest.toTypedArray())
    }

    private fun onGrantedPermission(){
        bill = getBill()
        if(Objects.nonNull(bill)){
            viewModel.saveBill(bill!!.billNumber, bill!!)
        }else{
            startProgressBar(false)
        }
    }

    private fun onDeniedPermission(){
        ToastUtils.showToast(requireContext(), Constants.Messages.PERMISSION_DENIED_GENERATE_BILL)
    }

    private fun addBillItem(recyclerView: RecyclerView){
        val adapter = recyclerView.adapter as BillAdapter
        adapter.appendData(BillItem())
    }

    private fun getBill(): Bill {
        ValidateIndividualFormUtils.validateBill(binding.root, requireContext(), resources)
        returnUiDataExtractorUtils.getBill(binding.root)
    }

    override fun update(sum: Int){
        billTotal+=sum
        binding.billTotal.text = "Rs. "+StringUtils.formatPrice(billTotal)
    }

    fun startProgressBar(isVisible: Boolean){
        binding.root.isClickable = !isVisible
        (activity as MainActivity).startProgressBar(isVisible)
    }
}