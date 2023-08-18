package com.samarthhms.ui

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.samarthhms.constants.Constants
import com.samarthhms.databinding.FragmentDischargeCardTemplateBinding
import com.samarthhms.models.MedicineTemplate
import com.samarthhms.models.PatientHistoryTemplate
import com.samarthhms.models.RecyclerViewAdapter
import com.samarthhms.utils.DialogUtils
import com.samarthhms.utils.RecyclerViewAdapterUtils
import com.samarthhms.utils.ToastUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DischargeCardTemplateFragment : Fragment(), RecyclerOnItemViewEditClickListener {

    private lateinit var binding: FragmentDischargeCardTemplateBinding

    private val viewModel: DischargeCardTemplateViewModel by viewModels()

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = FragmentDischargeCardTemplateBinding.inflate(layoutInflater, container, false)
        val medicineTemplateAdapter = MedicineTemplateAdapter(requireContext(), this, listOf())
        binding.medicineTemplatesRecyclerView.adapter = medicineTemplateAdapter
        val patientHistoryTemplateAdapter = PatientHistoryTemplateAdapter(requireContext(), this, listOf())
        binding.patientHistoryTemplatesRecyclerView.adapter = patientHistoryTemplateAdapter
        viewModel.dischargeCardTemplate.observe(viewLifecycleOwner){
            RecyclerViewAdapterUtils.updateData<MedicineTemplateListAdapter.MedicineTemplateHolder,MedicineTemplate>(binding.medicineTemplatesRecyclerView.adapter, viewModel.dischargeCardTemplate.value!!.medicineTemplates)
            RecyclerViewAdapterUtils.updateData<PatientHistoryTemplateAdapter.PatientHistoryTemplateHolder,PatientHistoryTemplate>(binding.patientHistoryTemplatesRecyclerView.adapter, viewModel.dischargeCardTemplate.value!!.patientHistoryTemplates)
        }
        viewModel.getData()
        binding.addMedicineTemplateButton.setOnClickListener{
            val medicineTemplates = (binding.medicineTemplatesRecyclerView.adapter as MedicineTemplateAdapter).templates.toMutableList()
            medicineTemplates.add(MedicineTemplate())
            RecyclerViewAdapterUtils.updateData<MedicineTemplateListAdapter.MedicineTemplateHolder,MedicineTemplate>(binding.medicineTemplatesRecyclerView.adapter, medicineTemplates)
        }

        binding.addPatientHistoryTemplateButton.setOnClickListener{
            val patientHistoryTemplates = (binding.patientHistoryTemplatesRecyclerView.adapter as PatientHistoryTemplateAdapter).templates.toMutableList()
            patientHistoryTemplates.add(PatientHistoryTemplate())
            RecyclerViewAdapterUtils.updateData<PatientHistoryTemplateAdapter.PatientHistoryTemplateHolder,PatientHistoryTemplate>(binding.patientHistoryTemplatesRecyclerView.adapter, patientHistoryTemplates)
        }
        return binding.root
    }

    override fun onEditClicked(data: Any) {
//        if(data is MedicineTemplate){
////
//        }
//        else if(data is PatientHistoryTemplate){
//
//        }
    }

    override fun onSaveClicked(data: Any) {
        if(data is MedicineTemplate){
            viewModel.addMedicineTemplate(data)
        }
        else if(data is PatientHistoryTemplate){
            viewModel.addPatientHistoryTemplate(data)
        }
    }

    override fun onInvalidData(message: String) {
        ToastUtils.showToast(requireContext(), message)
    }

    override fun onDeleteClicked(data: Any) {
        DialogUtils.popDialog(requireContext(), {onDelete(data)}, null, Constants.DialogMessages.DELETE_ADMIN, Constants.DialogMessages.YES, Constants.DialogMessages.NO)
    }

    private fun onDelete(data: Any){
        if(data is MedicineTemplate){
            viewModel.deleteMedicineTemplate(data.templateId)
        }
        else if(data is PatientHistoryTemplate){
            viewModel.deletePatientHistoryTemplate(data.templateId)
        }
    }

}