package com.samarthhms.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.widget.SearchView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.navigation.NavigationView
import com.google.firebase.firestore.FirebaseFirestore
import com.samarthhms.BuildConfig
import com.samarthhms.R
import com.samarthhms.constants.Gender
import com.samarthhms.constants.SchemaName
import com.samarthhms.databinding.FragmentAdminDashboardBinding
import com.samarthhms.models.*
import com.samarthhms.navigator.Navigator
import com.samarthhms.repository.AdminRepository
import com.samarthhms.repository.AdminRepositoryImpl
import com.samarthhms.repository.StoredStateDao
import com.samarthhms.repository.StoredStateRepositoryImpl
import com.samarthhms.service.GenerateBill
import com.samarthhms.service.GenerateDischargeCard
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject


@AndroidEntryPoint
class AdminDashboardFragment : Fragment(),RecyclerOnItemViewClickListener {
    private lateinit var binding: FragmentAdminDashboardBinding

    private val viewModel: AdminDashboardViewModel by viewModels()

    @Inject
    lateinit var navigator: Navigator

    @Inject
    lateinit var generateDischargeCard: GenerateDischargeCard

    @Inject
    lateinit var generateBill: GenerateBill

    @Inject
    lateinit var adminRepository: AdminRepositoryImpl

    @Inject
    lateinit var storedStateRepository: StoredStateRepositoryImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as MainActivity).binding.bottomNavigation.visibility = NavigationView.VISIBLE
        binding = FragmentAdminDashboardBinding.inflate(layoutInflater, container, false)
        startProgressBar(true)
        viewModel.updateGreetings()
        binding.extendedFab.setOnClickListener{
            val controller = findNavController()
            controller.navigate(R.id.action_adminDashboardFragment_to_addPatientFragment)
        }
        viewModel.updateData()
        viewModel.addListener()
        val adapter = VisitInfoAdapter(context, this, listOf())
        binding.patientsTodayRecyclerView.adapter = adapter
        viewModel.patientsTodayList.observe(viewLifecycleOwner){
            (binding.patientsTodayRecyclerView.adapter as VisitInfoAdapter).patientsToday = it
            (binding.patientsTodayRecyclerView.adapter as VisitInfoAdapter).notifyDataSetChanged()
        }
        viewModel.patientsTodayCount.observe(viewLifecycleOwner){
            binding.patientsTodayCountNumber.text = it.toString()
        }
        viewModel.unattendedPatientsCount.observe(viewLifecycleOwner){
            binding.unattendedPatientsCountNumber.text = it.toString()
        }
        viewModel.admitPatientsCount.observe(viewLifecycleOwner){
            binding.admitPatientsCountNumber.text = it.toString()
        }

        viewModel.greeting.observe(viewLifecycleOwner){
            binding.greeting.text = it.toString()
        }

        viewModel.userName.observe(viewLifecycleOwner){
            if(it.isNotBlank()){
                startProgressBar(false)
            }
            binding.adminName.text = it.toString()
        }

        binding.patientsTodayCountTitle.setOnClickListener{
            findNavController().navigate(R.id.action_adminDashboardFragment_to_patientsTodayFragment)
        }
/*
        binding.unattendedPatientsCount.setOnClickListener{
            val bottomSheetDialog = context?.let { it1 -> BottomSheetDialog(it1) }
            bottomSheetDialog?.setContentView(R.layout.bottom_sheet_layout)

            val searchView = bottomSheetDialog?.findViewById<SearchView>(R.id.search_view)
            val listView = bottomSheetDialog?.findViewById<>(R.id.search_list)
            val list = listOf(
                "IVF-RL / NP / DNS",
                "INJ. Cefbat-T/Maczone-XP (500 md ) IV BD—5days",
                "INJ . Amikacin (150mg) IV OD - 5 days",
                "INJ. Dexa (0.5 ml) Stat and SOS",
                "SYP. Macberry 2.5 ml TDS – 5 days",
                "INJ. Emset (0.5 ml) BD – 5 days",
                "INJ. Pan40 (2.5ml)– OD – 5 days",
                "Nebulization – 3% NS+INJ. Adrenaline BD – 2 days"
            )
            val adapter_ = ArrayAdapter<String>(requireContext(), R.layout.search_item_layout, R.id.item_value, list)
            listView?.adapter = adapter_

            searchView!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(s: String): Boolean {
                    return false
                }

                override fun onQueryTextChange(s: String): Boolean {
                    adapter_.filter.filter(s)
                    return false
                }
            })
            bottomSheetDialog?.show()
        }*/

//        binding.admitPatientsCount.setOnClickListener{
//            val dischargeCard = DischargeCard(
//                "SBP00001",
//                "37/23",
//                "Aadhrit",
//                "Sumit",
//                "Dandghvan",
//                Gender.MALE,
//                "98973099347",
//                10.3f,
//                92f,
//                0f,
//                "15M",
//                LocalDateTime.now(),
//                LocalDateTime.of(2023, 3, 2, 13, 0),
//                LocalDateTime.of(2023, 3, 6, 15, 0),
//                "Shivajinagar, Dindori, Tal. Dindori, Dist. Nashik",
//                "Croup",
//                patientHistory = "A 15 M old Mch b/b parents with c/o– High grade fever since day 2 (on/off),\n" +
//                        "cough cold since day 3 (barking), dout++, harshness of voice since day 1, difficulty in breathing since \n" +
//                        "12 hrs, decreased oral intake –1 days, decreased activity since 1 days. Taking oral medication since 2\n" +
//                        "days but no relief hence hospitalized.\n" +
//                        "O/E– febrile(102 F), dehydrated, lethargic++,PP– weak felt, throat congested .\n" +
//                        "S/E-- R/S –AEBE ICR+, Strider++, Nazal Flaring+, SPO2 96%, RMZ Scattered Crepts+ , CVS– HR\u0002112/ MIN, S1S2+ ,no murmur/gallop,CVS—Concious , oriented , no meningial sign.\n" +
//                        "P/A—Soft, Non tender no organomegaly\n",
//                pastHistory = "No h/o of hospitalization . No major illness in past.",
//                familyHistory = "Not significant. No family h/o of asthma.",
//                course = listOf(
//                    "IVF-RL / NP / DNS",
//                    "INJ. Cefbat-T/Maczone-XP (500 md ) IV BD—5days",
//                    "INJ . Amikacin (150mg) IV OD - 5 days",
//                    "INJ. Dexa (0.5 ml) Stat and SOS",
//                    "SYP. Macberry 2.5 ml TDS – 5 days",
//                    "INJ. Emset (0.5 ml) BD – 5 days",
//                    "INJ. Pan40 (2.5ml)– OD – 5 days",
//                    "Nebulization – 3% NS+INJ. Adrenaline BD – 2 days"
//                ),
//                investigations = "—2/3/23—hb-12.5, WBC –9700 Platelet count-224000, CRP- 18.2, Other Reports and test X Ray reports attached with discharge card",
//                medicationsOnDischarge = listOf(
//                    "IVF-RL / NP / DNS",
//                    "INJ. Cefbat-T/Maczone-XP (500 md ) IV BD—5days",
//                    "INJ . Amikacin (150mg) IV OD - 5 days",
//                    "INJ. Dexa (0.5 ml) Stat and SOS",
//                    "SYP. Macberry 2.5 ml TDS – 5 days",
//                    "INJ. Emset (0.5 ml) BD – 5 days",
//                    "INJ. Pan40 (2.5ml)– OD – 5 days",
//                    "Nebulization – 3% NS+INJ. Adrenaline BD – 2 days"
//                ),
//                advice = listOf(
//                "Vaccination (PCV13/influenza)",
//                        "Avoid oily & junk food",
//                        "follow up after 5 days.(11/3/23 with appointment)"
//                )
//
//            )
//            ActivityCompat.requestPermissions(requireActivity(),arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE), 2)
//            if(PackageManager.PERMISSION_GRANTED != context?.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ||
//               PackageManager.PERMISSION_GRANTED != context?.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)){
//                return@setOnClickListener
//            }
//            generateDischargeCard.generatePdf(dischargeCard)
//            val uriPath = FileProvider.getUriForFile(requireContext(), BuildConfig.APPLICATION_ID +".provider", generateDischargeCard.file!!)
//
//            val pdfIntent = Intent(Intent.ACTION_VIEW)
//            pdfIntent.setDataAndType(uriPath, "application/pdf")
//            pdfIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
//            pdfIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
//            pdfIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
//            startActivity(pdfIntent)
//        }

        binding.admitPatientsCount.setOnClickListener{
            val treatmentCharges = listOf<BillItem>(
                BillItem("Registration", 350, 1),
                BillItem("Consultation/ Doctors/ Round charges", 800, 5),
                BillItem("Nursing charges", 450, 5),
                BillItem("Room/ Bed charges (Special room)", 1500, 5),
                BillItem("BMW", 100, 5)
            )

            val managementCharges = listOf<BillItem>(
                BillItem("Procedure IV Canula charges", 200, 2),
                BillItem("Fluid management", 200, 5),
                BillItem("Emergency charges", 500, 1),
                BillItem("Nebulization charges", 20, 50)
            )

            val bill = Bill(
                "SBP00001",
                "",
                "=",
                "06/23",
                "Aadhrit",
                "Sumit",
                "Dandghvan",
                Gender.MALE,
                "15M",
                "9999999999",
                LocalDateTime.of(2023, 3, 2, 13, 0),
                LocalDateTime.of(2023, 3, 6, 15, 0),
                "Shivajinagar, Dindori, Tal. Dindori, Dist. Nashik",
                "Croup",
                treatmentCharges,
                managementCharges,
                BillItem("Other Charges"),
                17500
            )
            ActivityCompat.requestPermissions(requireActivity(),arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE), 2)
            if(PackageManager.PERMISSION_GRANTED != context?.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ||
                PackageManager.PERMISSION_GRANTED != context?.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                return@setOnClickListener
            }
            generateBill.generatePdf(bill)
            val uriPath = FileProvider.getUriForFile(requireContext(), BuildConfig.APPLICATION_ID +".provider", generateBill.file!!)

            val pdfIntent = Intent(Intent.ACTION_VIEW)
            pdfIntent.setDataAndType(uriPath, "application/pdf")
            pdfIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            pdfIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            pdfIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
            startActivity(pdfIntent)
        }

        return binding.root
    }

    override fun onItemClicked(data: Any?, requester: String) {
    }

    fun startProgressBar(isVisible: Boolean){
        binding.root.isClickable = !isVisible
        (activity as MainActivity).startProgressBar(isVisible)
    }


}