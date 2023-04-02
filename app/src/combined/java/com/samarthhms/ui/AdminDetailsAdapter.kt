package com.samarthhms.ui

import android.content.Context
import android.telecom.Call.Details
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Timestamp
import com.samarthhms.R
import com.samarthhms.databinding.PatientInfoLayoutBinding
import com.samarthhms.databinding.SwitchAdminLayoutBinding
import com.samarthhms.databinding.VisitInfoLayoutBinding
import com.samarthhms.domain.SwitchAdmin
import com.samarthhms.models.AdminDetails
import com.samarthhms.models.Patient
import com.samarthhms.models.PatientVisitInfo
import com.samarthhms.models.SwitchAdminData
import com.samarthhms.utils.DateTimeUtils
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Period

class AdminDetailsAdapter internal constructor(var recyclerOnItemViewClickListener: RecyclerOnItemViewClickListener, var admins: List<AdminDetails>) : RecyclerView.Adapter<AdminDetailsAdapter.AdminHolder>() {
    override fun onBindViewHolder(adminHolder: AdminDetailsAdapter.AdminHolder, position: Int) {
        adminHolder.bind(admins[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdminDetailsAdapter.AdminHolder {
        val adminLayoutBinding = SwitchAdminLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AdminHolder(adminLayoutBinding)
    }

    override fun getItemCount(): Int {
        return admins.size
    }

    inner class AdminHolder internal constructor(private val adminLayoutBinding: SwitchAdminLayoutBinding) : RecyclerView.ViewHolder(adminLayoutBinding.root) {
        fun bind(adminDetails: AdminDetails) {
            val admin = adminDetails.admin
            val switchAdmin = adminDetails.switchAdminData!!
            adminLayoutBinding.adminName.text = admin?.firstName + " " + admin?.lastName
            adminLayoutBinding.selfTitle.visibility = if(switchAdmin.isAccountOwner) View.VISIBLE else View.GONE
            adminLayoutBinding.switchStatus.visibility = if(switchAdmin.isCurrentUser) View.VISIBLE else View.GONE
            adminLayoutBinding.infoBlock.setOnClickListener {
                recyclerOnItemViewClickListener.onItemClicked(adminDetails)
            }
        }
    }
}