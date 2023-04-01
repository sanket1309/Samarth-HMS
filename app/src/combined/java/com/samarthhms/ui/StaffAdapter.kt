package com.samarthhms.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Timestamp
import com.samarthhms.R
import com.samarthhms.databinding.PatientInfoLayoutBinding
import com.samarthhms.databinding.StaffLayoutBinding
import com.samarthhms.databinding.SwitchAdminLayoutBinding
import com.samarthhms.databinding.VisitInfoLayoutBinding
import com.samarthhms.domain.SwitchAdmin
import com.samarthhms.models.Patient
import com.samarthhms.models.PatientVisitInfo
import com.samarthhms.models.StaffDetails
import com.samarthhms.models.SwitchAdminData
import com.samarthhms.utils.DateTimeUtils
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Period

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