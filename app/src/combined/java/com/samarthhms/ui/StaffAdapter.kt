package com.samarthhms.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.samarthhms.databinding.StaffLayoutBinding
import com.samarthhms.models.StaffDetails

class StaffAdapter internal constructor(var recyclerOnItemViewClickListener: RecyclerOnItemViewClickListener, var staff: List<StaffDetails>) : RecyclerView.Adapter<StaffAdapter.StaffHolder>() {
    override fun onBindViewHolder(adminHolder: StaffAdapter.StaffHolder, position: Int) {
        adminHolder.bind(staff[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StaffAdapter.StaffHolder {
        val staffLayoutBinding = StaffLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StaffHolder(staffLayoutBinding)
    }

    override fun getItemCount(): Int {
        return staff.size
    }

    inner class StaffHolder internal constructor(private val staffLayoutBinding: StaffLayoutBinding) : RecyclerView.ViewHolder(staffLayoutBinding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(staffDetails: StaffDetails) {
            staffLayoutBinding.adminName.text = staffDetails.staff?.firstName + " " + staffDetails.staff?.lastName
            staffLayoutBinding.lockImage.visibility = if(staffDetails.staffStatus!!.isLocked) View.VISIBLE else View.GONE
            staffLayoutBinding.unlockImage.visibility = if(!staffDetails.staffStatus!!.isLocked) View.VISIBLE else View.GONE
            staffLayoutBinding.infoBlock.setOnClickListener{
                recyclerOnItemViewClickListener.onItemClicked(staffDetails, "Details")
            }
            staffLayoutBinding.lockImage.setOnClickListener{
                recyclerOnItemViewClickListener.onItemClicked(staffDetails, "LOCK")
            }
            staffLayoutBinding.unlockImage.setOnClickListener{
                recyclerOnItemViewClickListener.onItemClicked(staffDetails, "UNLOCK")
            }
        }
    }
}