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
import com.samarthhms.databinding.FragmentDischargeCardTemplateBinding
import com.samarthhms.models.MedicineTemplate
import com.samarthhms.models.PatientHistoryTemplate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DischargeCardTemplateFragment : Fragment(), RecyclerOnItemViewEditClickListener {

    private lateinit var binding: FragmentDischargeCardTemplateBinding

    private val viewModel: DischargeCardTemplateViewModel by viewModels()

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDischargeCardTemplateBinding.inflate(layoutInflater, container, false)
        val medicineTemplateAdapter = MedicineTemplateAdapter(requireContext(), this, listOf())
        binding.medicineTemplatesRecyclerView.adapter = medicineTemplateAdapter
        val patientHistoryTemplateAdapter = PatientHistoryTemplateAdapter(requireContext(), this, listOf())
        binding.patientHistoryTemplatesRecyclerView.adapter = patientHistoryTemplateAdapter
        viewModel.dischargeCardTemplate.observe(viewLifecycleOwner){
            (binding.medicineTemplatesRecyclerView.adapter as MedicineTemplateAdapter).templates = viewModel.dischargeCardTemplate.value!!.medicineTemplates
            (binding.medicineTemplatesRecyclerView.adapter as MedicineTemplateAdapter).notifyDataSetChanged()
            (binding.patientHistoryTemplatesRecyclerView.adapter as PatientHistoryTemplateAdapter).templates = viewModel.dischargeCardTemplate.value!!.patientHistoryTemplates
            (binding.patientHistoryTemplatesRecyclerView.adapter as PatientHistoryTemplateAdapter).notifyDataSetChanged()
        }
        viewModel.getData()
        binding.addMedicineTemplateButton.setOnClickListener{
            val medicineTemplates = (binding.medicineTemplatesRecyclerView.adapter as MedicineTemplateAdapter).templates.toMutableList()
            medicineTemplates.add(MedicineTemplate("","ADD_NEW_TEMPLATE_DEFAULT"))
            (binding.medicineTemplatesRecyclerView.adapter as MedicineTemplateAdapter).templates = medicineTemplates
            (binding.medicineTemplatesRecyclerView.adapter as MedicineTemplateAdapter).notifyDataSetChanged()
        }

        binding.addPatientHistoryTemplateButton.setOnClickListener{
            val patientHistoryTemplates = (binding.patientHistoryTemplatesRecyclerView.adapter as PatientHistoryTemplateAdapter).templates.toMutableList()
            patientHistoryTemplates.add(PatientHistoryTemplate("","","ADD_NEW_TEMPLATE_DEFAULT"))
            (binding.patientHistoryTemplatesRecyclerView.adapter as PatientHistoryTemplateAdapter).templates = patientHistoryTemplates
            (binding.patientHistoryTemplatesRecyclerView.adapter as PatientHistoryTemplateAdapter).notifyDataSetChanged()
        }
        return binding.root
    }

    override fun onEditClicked(data: Any) {
        if(data is MedicineTemplate){

        }
        else if(data is PatientHistoryTemplate){

        }
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
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDeleteClicked(data: Any) {
        val dialogClickListener = DialogInterface.OnClickListener{
                dialog, which ->
            when(which){
                DialogInterface.BUTTON_POSITIVE -> {
                    if(data is MedicineTemplate){
                        viewModel.deleteMedicineTemplate(data.templateId)
                    }
                    else if(data is PatientHistoryTemplate){
                        viewModel.deletePatientHistoryTemplate(data.templateId)
                    }
                }
                DialogInterface.BUTTON_NEGATIVE -> {
                    dialog.dismiss()
                }
            }
        }
        val alertDialogBuilder = AlertDialog.Builder(requireContext())
        alertDialogBuilder.setMessage("Are you sure, you want to delete template?")
            .setPositiveButton("Yes", dialogClickListener)
            .setNegativeButton("No", dialogClickListener)
            .show()
    }

}