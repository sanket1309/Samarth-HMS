package com.samarthhms.ui

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.viewModels
import com.samarthhms.R
import com.samarthhms.databinding.FragmentDischargeCardTemplateBinding
import com.samarthhms.databinding.MedicineTemplateLayoutBinding
import com.samarthhms.domain.Status
import com.samarthhms.models.MedicineTemplate
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
        val medicineTemplateAdapter = MedicineTemplateAdapter(context!!, this, listOf())
        binding.medicineTemplatesRecyclerView.adapter = medicineTemplateAdapter
        viewModel.dischargeCardTemplate.observe(viewLifecycleOwner){
            Log.i("CHECK","RESULT :-\n${viewModel.dischargeCardTemplate.value!!.medicineTemplates}")
            (binding.medicineTemplatesRecyclerView.adapter as MedicineTemplateAdapter).templates = viewModel.dischargeCardTemplate.value!!.medicineTemplates
            (binding.medicineTemplatesRecyclerView.adapter as MedicineTemplateAdapter).notifyDataSetChanged()
        }
        viewModel.getData()
        binding.addMedicineTemplateButton.setOnClickListener{
            var templates = (binding.medicineTemplatesRecyclerView.adapter as MedicineTemplateAdapter).templates.toMutableList()
            templates.add(MedicineTemplate("","ADD_NEW_TEMPLATE_DEFAULT"))
            (binding.medicineTemplatesRecyclerView.adapter as MedicineTemplateAdapter).templates = templates
            (binding.medicineTemplatesRecyclerView.adapter as MedicineTemplateAdapter).notifyDataSetChanged()
            activity?.findViewById<DrawerLayout>(R.id.drawer_layout)?.closeDrawer(GravityCompat.START, true)
        }
        return binding.root
    }

    override fun onEditClicked(data: Any) {
        if(data is MedicineTemplate){

        }
    }

    override fun onSaveClicked(data: Any) {
        if(data is MedicineTemplate){
            viewModel.addMedicineTemplate(data)
        }
    }

    override fun onInvalidData(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDeleteClicked(data: Any) {
        TODO("Not yet implemented")
    }

}