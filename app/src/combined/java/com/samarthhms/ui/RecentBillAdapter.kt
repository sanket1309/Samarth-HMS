package com.samarthhms.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Timestamp
import com.samarthhms.R
import com.samarthhms.databinding.BillInfoLayoutBinding
import com.samarthhms.databinding.PatientInfoLayoutBinding
import com.samarthhms.databinding.VisitInfoLayoutBinding
import com.samarthhms.models.Bill
import com.samarthhms.models.Patient
import com.samarthhms.models.PatientVisitInfo
import com.samarthhms.utils.DateTimeUtils
import com.samarthhms.utils.StringUtils
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Period

class RecentBillAdapter internal constructor(var context: Context?, var recyclerOnItemViewClickListener: RecyclerOnItemViewClickListener, var bills: List<Bill>) : RecyclerView.Adapter<RecentBillAdapter.BillHolder>() {
    override fun onBindViewHolder(patientHolder: RecentBillAdapter.BillHolder, position: Int) {
        patientHolder.bind(bills[position])
    }

        override fun getItemViewType(position: Int): Int {
            return (itemCount-position-1)%3
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentBillAdapter.BillHolder {
        val billInfoLayoutBinding = BillInfoLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val resources = context?.resources
        when(viewType){
            0 -> billInfoLayoutBinding.infoBlock.background = resources?.getDrawable(R.drawable.patient_info_background_red,null)
            1 -> billInfoLayoutBinding.infoBlock.background = resources?.getDrawable(R.drawable.patient_info_background_green,null)
            2 -> billInfoLayoutBinding.infoBlock.background = resources?.getDrawable(R.drawable.patient_info_background_blue,null)
        }
        return BillHolder(billInfoLayoutBinding)
    }

    override fun getItemCount(): Int {
        return bills.size
    }

    inner class BillHolder internal constructor(private val billInfoLayoutBinding: BillInfoLayoutBinding) : RecyclerView.ViewHolder(billInfoLayoutBinding.root) {
        fun bind(bill: Bill) {
            billInfoLayoutBinding.billNumber.text = StringUtils.formatYearWiseIdSpacePadded(bill.billNumber)
            billInfoLayoutBinding.patientName.text = bill.firstName + " " + bill.lastName
            billInfoLayoutBinding.patientGender.text = bill.gender.value + ", "
            billInfoLayoutBinding.patientAge.text = bill.age
            billInfoLayoutBinding.diagnosis.text = bill.diagnosis
            billInfoLayoutBinding.infoBlock.setOnClickListener{
                recyclerOnItemViewClickListener.onItemClicked(bill)
            }
        }
    }
}