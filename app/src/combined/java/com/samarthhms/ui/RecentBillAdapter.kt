package com.samarthhms.ui

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.samarthhms.R
import com.samarthhms.databinding.BillInfoLayoutBinding
import com.samarthhms.models.Bill
import com.samarthhms.utils.StringUtils

class RecentBillAdapter internal constructor(var context: Context?, var recyclerOnItemViewClickListener: RecyclerOnItemViewClickListener, var bills: List<Bill>) : RecyclerView.Adapter<RecentBillAdapter.BillHolder>() {
    override fun onBindViewHolder(patientHolder: RecentBillAdapter.BillHolder, position: Int) {
        patientHolder.bind(bills[position])
    }

        override fun getItemViewType(position: Int): Int {
            return (itemCount-position-1)%3
        }

    @SuppressLint("UseCompatLoadingForDrawables")
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
        @SuppressLint("SetTextI18n")
        fun bind(bill: Bill) {
            billInfoLayoutBinding.billNumber.text = StringUtils.formatYearWiseIdSpacePadded(bill.billNumber)
            billInfoLayoutBinding.patientName.text = bill.firstName + " " + bill.lastName
            billInfoLayoutBinding.patientGender.text = bill.gender.value + ", "
            billInfoLayoutBinding.patientAge.text = bill.age
            billInfoLayoutBinding.diagnosis.text = bill.diagnosis
            billInfoLayoutBinding.infoBlock.setOnClickListener{
                recyclerOnItemViewClickListener.onItemClicked(bill, isLongPress = false)
            }
            billInfoLayoutBinding.infoBlock.setOnLongClickListener{
                recyclerOnItemViewClickListener.onItemClicked(bill, isLongPress = true)
                true
            }
        }
    }
}