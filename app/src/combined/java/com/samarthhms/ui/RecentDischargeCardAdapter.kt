package com.samarthhms.ui

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.samarthhms.R
import com.samarthhms.databinding.DischargeCardInfoLayoutBinding
import com.samarthhms.models.DischargeCard
import com.samarthhms.utils.StringUtils

class RecentDischargeCardAdapter internal constructor(var context: Context?, var recyclerOnItemViewClickListener: RecyclerOnItemViewClickListener, var dischargeCards: List<DischargeCard>) : RecyclerView.Adapter<RecentDischargeCardAdapter.DischargeCardHolder>() {
    override fun onBindViewHolder(patientHolder: DischargeCardHolder, position: Int) {
        patientHolder.bind(dischargeCards[position])
    }

    override fun getItemViewType(position: Int): Int {
        return (itemCount-position-1)%3
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DischargeCardHolder {
        val dischargeCardInfoLayoutBinding = DischargeCardInfoLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val resources = context?.resources
        when(viewType){
            0 -> dischargeCardInfoLayoutBinding.infoBlock.background = resources?.getDrawable(R.drawable.patient_info_background_red,null)
            1 -> dischargeCardInfoLayoutBinding.infoBlock.background = resources?.getDrawable(R.drawable.patient_info_background_green,null)
            2 -> dischargeCardInfoLayoutBinding.infoBlock.background = resources?.getDrawable(R.drawable.patient_info_background_blue,null)
        }
        return DischargeCardHolder(dischargeCardInfoLayoutBinding)
    }

    override fun getItemCount(): Int {
        return dischargeCards.size
    }

    inner class DischargeCardHolder internal constructor(private val dischargeCardInfoLayoutBinding: DischargeCardInfoLayoutBinding) : RecyclerView.ViewHolder(dischargeCardInfoLayoutBinding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(dischargeCard: DischargeCard) {
            dischargeCardInfoLayoutBinding.ipdNumber.text = StringUtils.formatYearWiseIdSpacePadded(dischargeCard.ipdNumber)
            dischargeCardInfoLayoutBinding.patientName.text = dischargeCard.firstName + " " + dischargeCard.lastName
            dischargeCardInfoLayoutBinding.patientGender.text = dischargeCard.gender.value + ", "
            dischargeCardInfoLayoutBinding.patientAge.text = dischargeCard.ageFormat
            dischargeCardInfoLayoutBinding.diagnosis.text = dischargeCard.diagnosis
            dischargeCardInfoLayoutBinding.infoBlock.setOnClickListener{
                recyclerOnItemViewClickListener.onItemClicked(dischargeCard, isLongPress = false)
            }
            dischargeCardInfoLayoutBinding.infoBlock.setOnLongClickListener{
                recyclerOnItemViewClickListener.onItemClicked(dischargeCard, isLongPress = true)
                true
            }
        }
    }
}