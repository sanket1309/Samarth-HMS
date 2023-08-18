package com.samarthhms.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.samarthhms.R
import com.samarthhms.databinding.FragmentAddVisitBinding
import com.samarthhms.domain.Status
import com.samarthhms.navigator.Navigator
import com.samarthhms.utils.DateTimeUtils
import com.samarthhms.utils.UiDataDisplayUtils
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AddVisitFragment : Fragment() {

    private val addVisitViewModel: AddVisitViewModel by viewModels()

    @Inject
    lateinit var navigator : Navigator

    private lateinit var binding: FragmentAddVisitBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddVisitBinding.inflate(layoutInflater, container, false)
        val patient = AddVisitFragmentArgs.fromBundle(requireArguments()).patient
        UiDataDisplayUtils.displayPatient(binding.root, patient)
        //TODO ADD TO DISPLAY CHARGES HERE - WILL HAVE TO GET VISIT OBJECT IN ARGUMENTS

        binding.addVisitButton.setOnClickListener{
            addVisitViewModel.addVisit(patient)
        }

        addVisitViewModel.addVisitStatus.observe(viewLifecycleOwner){
            when(it){
                Status.SUCCESS -> onSuccess()
                Status.FAILURE -> onFailure()
                else -> {}
            }
        }

        return binding.root
    }

    private fun onSuccess(){
        Toast.makeText(activity, "Added Visit Successfully", Toast.LENGTH_SHORT).show()
        findNavController().popBackStack(R.id.addPatientFragment, true)
    }

    private fun onFailure(){
        Toast.makeText(activity, "Something Went Wrong", Toast.LENGTH_SHORT).show()
    }

}