package com.samarthhms.ui

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.drawable.DrawableContainer
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.StateListDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Filterable
import androidx.appcompat.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.samarthhms.R
import com.samarthhms.constants.Constants
import com.samarthhms.constants.Gender
import com.samarthhms.databinding.FragmentGenerateDischargeCardBinding
import com.samarthhms.domain.Status
import com.samarthhms.models.DischargeCard
import com.samarthhms.models.DischargeCardTemplate
import com.samarthhms.models.MedicineTemplate
import com.samarthhms.models.PatientHistoryTemplate
import com.samarthhms.utils.*
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDateTime
import java.util.*


@AndroidEntryPoint
class GenerateDischargeCardFragment : Fragment(), RecyclerOnItemViewClickListener {

    private val viewModel: GenerateDischargeCardViewModel by viewModels()

    private lateinit var binding: FragmentGenerateDischargeCardBinding

    private var bottomSheetDialog: BottomSheetDialog? = null

    private var dischargeCardTemplate: DischargeCardTemplate? = null

    private var courseMedicineTemplateRequester = "courseMedicineTemplateRequester"

    private var medicationMedicineTemplateRequester = "medicationMedicineTemplateRequester"

    private var dischargeCard: DischargeCard? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGenerateDischargeCardBinding.inflate(layoutInflater, container, false)
        binding.courseList.adapter = MedicineTemplateListAdapter(mutableListOf())
        binding.medicationList.adapter = MedicineTemplateListAdapter(mutableListOf())
        binding.adviceList.adapter = MedicineTemplateListAdapter(Constants.DischargeCardConstants.ADVICE_LIST.toMutableList().map { it.templateData }.toMutableList())

        startProgressBar(true)
        viewModel.getData()

        viewModel.dischargeCardTemplate.observe(viewLifecycleOwner){
            if(viewModel.getDischargeCardTemplateStatus.value == Status.SUCCESS){
                dischargeCardTemplate = viewModel.dischargeCardTemplate.value
                startProgressBar(false)
            }else if(viewModel.getDischargeCardTemplateStatus.value == Status.FAILURE){
                dischargeCardTemplate = viewModel.dischargeCardTemplate.value
                startProgressBar(false)
                ToastUtils.showToast(requireContext(), Constants.Messages.SOMETHING_WENT_WRONG)
            }
        }

        binding.addPatientHistoryButton.setOnClickListener{
            if(dischargeCardTemplate!=null){
                val adapter = PatientHistoryTemplateSearchAdapter(this, dischargeCardTemplate!!.patientHistoryTemplates)
                bottomSheetDialog = DialogUtils.popBottomSheetDialog(adapter,requireContext())
            }
        }

        binding.addCourseButton.setOnClickListener{
            if(dischargeCardTemplate!=null){
                val adapter = MedicineTemplateSearchAdapter(this, dischargeCardTemplate!!.medicineTemplates, requester =  courseMedicineTemplateRequester)
                bottomSheetDialog = DialogUtils.popBottomSheetDialog(adapter,requireContext())
            }
        }

        binding.addMedicationButton.setOnClickListener{
            if(dischargeCardTemplate!=null){
                val adapter = MedicineTemplateSearchAdapter(this, dischargeCardTemplate!!.medicineTemplates, requester =  medicationMedicineTemplateRequester)
                bottomSheetDialog = DialogUtils.popBottomSheetDialog(adapter,requireContext())
            }
        }

        binding.addAdviceButton.setOnClickListener{
            if(dischargeCardTemplate!=null){
                (binding.adviceList.adapter as MedicineTemplateListAdapter).appendData(Constants.EMPTY)
                bottomSheetDialog?.cancel()
            }
        }

        binding.generateDischargeCardButton.setOnClickListener{
            startProgressBar(true)
            dischargeCard = getDischargeCard()
            if(Objects.nonNull(dischargeCard)){
                viewModel.saveDischargeCard(dischargeCard!!.ipdNumber,dischargeCard!!)
            }else{
                startProgressBar(false)
            }
        }

        viewModel.getDischargeCardStatus.observe(viewLifecycleOwner){
            if(it == Status.NONE){
                startProgressBar(false)
                return@observe
            }
            if(it == Status.SUCCESS && viewModel.dischargeCardFile.value != null){
                startProgressBar(false)
                val action = GenerateDischargeCardFragmentDirections.actionGenerateDischargeCardFragmentToPdfDetailsFragment(viewModel.dischargeCardFile.value!!)
                findNavController().navigate(action)
            }
        }

        viewModel.saveDischargeCardStatus.observe(viewLifecycleOwner){
            if(it == Status.NONE){
                startProgressBar(false)
                return@observe
            }else if(it == Status.IPD_NUMBER_ALREADY_EXISTS){
                DialogUtils.popDialogWithMessageOnly(requireContext(),Constants.Messages.IPD_NUMBER_ALREADY_EXISTS)
                return@observe
            }else if(it == Status.SUCCESS){
                startProgressBar(false)
                viewModel.makeDischargeCard(dischargeCard!!)
            }
        }
        return binding.root
    }

    private fun popDialog(message: String){
        val alertDialogBuilder = AlertDialog.Builder(requireContext())
        alertDialogBuilder.setMessage(message)
            .setCancelable(true)
            .show()
    }

    private fun getDischargeCard(): DischargeCard {
        try{
            ValidateIndividualFormUtils.validateDischargeCard(binding.root, requireContext(), resources)
            return UiDataExtractorUtils.getDischargeCard(binding.root)
        }catch (e: Exception){
            ToastUtils.showToast(requireContext(),Constants.Messages.SOMETHING_WENT_WRONG)
            Log.e("GenerateDischargeCardFragment","Error while getting discharge card")
            throw e
        }
    }

    override fun onItemClicked(data: Any?, requester: String) {
        if(data is PatientHistoryTemplate){
            binding.patientHistory.setText(data.templateData)
            bottomSheetDialog?.cancel()
        }
        else if(data is String && requester == courseMedicineTemplateRequester){
            (binding.courseList.adapter as MedicineTemplateListAdapter).appendData(data)
            bottomSheetDialog?.cancel()
        }
        else if(data is String && requester == medicationMedicineTemplateRequester){
            (binding.medicationList.adapter as MedicineTemplateListAdapter).appendData(data)
            bottomSheetDialog?.cancel()
        }
    }

    fun startProgressBar(isVisible: Boolean){
//        binding.root.isClickable = !isVisible
        (activity as MainActivity).startProgressBar(isVisible)
    }

}