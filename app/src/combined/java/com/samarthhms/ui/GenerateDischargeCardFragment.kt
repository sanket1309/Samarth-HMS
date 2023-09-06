package com.samarthhms.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.samarthhms.constants.Constants
import com.samarthhms.databinding.FragmentGenerateDischargeCardBinding
import com.samarthhms.domain.Status
import com.samarthhms.models.DischargeCard
import com.samarthhms.models.DischargeCardTemplate
import com.samarthhms.models.PatientHistoryTemplate
import com.samarthhms.navigator.Navigator
import com.samarthhms.utils.*
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject


@AndroidEntryPoint
class GenerateDischargeCardFragment : Fragment(), RecyclerOnItemViewClickListener {

    private val viewModel: GenerateDischargeCardViewModel by viewModels()

    @Inject
    lateinit var navigator: Navigator

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

        viewModel.dischargeCardTemplate.observe(viewLifecycleOwner){ onDischargeCardTemplate(it) }
        binding.addPatientHistoryButton.setOnClickListener{ onAddPatientHistory(it) }
        binding.addCourseButton.setOnClickListener{ onAddCourse(it) }
        binding.addMedicationButton.setOnClickListener{ onAddMedication(it) }
        binding.addAdviceButton.setOnClickListener{ onAddAdvice(it) }
        binding.generateDischargeCardButton.setOnClickListener{ onGenerateDischargeCard(it) }
        viewModel.getDischargeCardStatus.observe(viewLifecycleOwner){ onGetDischargeCardStatus(it)}
        viewModel.saveDischargeCardStatus.observe(viewLifecycleOwner){ onSaveDischargeCardStatus(it) }

        return binding.root
    }

    fun onDischargeCardTemplate(it: DischargeCardTemplate){
        if(viewModel.getDischargeCardTemplateStatus.value == Status.SUCCESS){
            dischargeCardTemplate = viewModel.dischargeCardTemplate.value
            startProgressBar(false)
        }else if(viewModel.getDischargeCardTemplateStatus.value == Status.FAILURE){
            dischargeCardTemplate = viewModel.dischargeCardTemplate.value
            startProgressBar(false)
            ToastUtils.showToast(requireContext(), Constants.Messages.SOMETHING_WENT_WRONG)
        }
    }

    fun onAddPatientHistory(view: View){
        if(dischargeCardTemplate!=null){
            val adapter = PatientHistoryTemplateSearchAdapter(this, dischargeCardTemplate!!.patientHistoryTemplates)
            bottomSheetDialog = DialogUtils.popBottomSheetDialog(adapter,requireContext())
        }
    }

    fun onAddCourse(view: View){
        if(dischargeCardTemplate!=null){
            val adapter = PatientHistoryTemplateSearchAdapter(this, dischargeCardTemplate!!.patientHistoryTemplates)
            bottomSheetDialog = DialogUtils.popBottomSheetDialog(adapter,requireContext())
        }
    }

    fun onAddMedication(view: View){
        if(dischargeCardTemplate!=null){
            val adapter = MedicineTemplateSearchAdapter(this, dischargeCardTemplate!!.medicineTemplates, requester = courseMedicineTemplateRequester)
            bottomSheetDialog = DialogUtils.popBottomSheetDialog(adapter,requireContext())
        }
    }

    fun onAddAdvice(view: View) {
        if (dischargeCardTemplate != null) {
            val templates = (binding.adviceList.adapter as MedicineTemplateListAdapter).templates
            templates.add(Constants.EMPTY)
            (binding.adviceList.adapter as MedicineTemplateListAdapter).appendData(Constants.EMPTY)
            bottomSheetDialog?.cancel()
        }
    }

    protected fun onGenerateDischargeCard(view: View) {
        startProgressBar(true)
        this.dischargeCard = getDischargeCard()
        if(Objects.nonNull(this.dischargeCard)){
            viewModel.saveDischargeCard(dischargeCard!!.ipdNumber, this.dischargeCard!!, isNewCard = false)
        }else{
            startProgressBar(false)
        }
    }

    protected fun onGetDischargeCardStatus(status: Status) {
        if(status == Status.NONE){
            startProgressBar(false)
            return
        } else if(status == Status.SUCCESS && viewModel.dischargeCardFile.value != null){
            startProgressBar(false)
            navigator.navigateToFragment(this, EditDischargeCardFragmentDirections.actionEditDischargeCardFragmentToPdfDetailsFragment(viewModel.dischargeCardFile.value!!))
        }
    }

    fun onSaveDischargeCardStatus(status: Status) {
        if(status == Status.NONE){
            startProgressBar(false)
            return
        } else if(status == Status.SUCCESS && viewModel.dischargeCardFile.value != null){
            startProgressBar(false)
            navigator.navigateToFragment(this, EditDischargeCardFragmentDirections.actionEditDischargeCardFragmentToPdfDetailsFragment(viewModel.dischargeCardFile.value!!))
        }
    }

    private fun getDischargeCard(): DischargeCard {
        try{
            ValidationUtils.validateDischargeCard(binding.root, requireContext(), resources)
            return UiDataExtractorUtils.getDischargeCard(binding.root)
        }catch (e: Exception){
            ToastUtils.showToast(requireContext(),Constants.Messages.SOMETHING_WENT_WRONG)
            Log.e("EditDischargeCardFragment","Error while getting discharge card")
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