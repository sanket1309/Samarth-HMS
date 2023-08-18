package com.samarthhms.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.samarthhms.constants.Constants
import com.samarthhms.databinding.StaffLayoutBinding
import com.samarthhms.models.RecyclerViewAdapter
import com.samarthhms.models.StaffDetails
import com.samarthhms.utils.UiDataDisplayUtils

class StaffAdapter internal constructor(var recyclerOnItemViewClickListener: RecyclerOnItemViewClickListener, var staff: List<StaffDetails> = listOf())
    : RecyclerViewAdapter<StaffAdapter.StaffHolder, StaffDetails>(staff.toMutableList()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StaffAdapter.StaffHolder {
        val staffLayoutBinding = StaffLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StaffHolder(staffLayoutBinding)
    }

    inner class StaffHolder internal constructor(private val staffLayoutBinding: StaffLayoutBinding)
        : RecyclerViewAdapter<StaffAdapter.StaffHolder, StaffDetails>.ViewHolder(staffLayoutBinding.root) {
        override fun bind(staffDetails: StaffDetails) {
            UiDataDisplayUtils.displayStaffDetails(staffLayoutBinding.root, staffDetails)
            staffLayoutBinding.infoBlock.setOnClickListener{ recyclerOnItemViewClickListener.onItemClicked(staffDetails, "Details") }
            staffLayoutBinding.lockImage.setOnClickListener{ recyclerOnItemViewClickListener.onItemClicked(staffDetails, Constants.LOCK) }
            staffLayoutBinding.unlockImage.setOnClickListener{ recyclerOnItemViewClickListener.onItemClicked(staffDetails, Constants.UNLOCK) }
        }
    }
}