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
import com.samarthhms.databinding.SwitchAdminLayoutBinding
import com.samarthhms.databinding.VisitInfoLayoutBinding
import com.samarthhms.domain.SwitchAdmin
import com.samarthhms.models.Patient
import com.samarthhms.models.PatientVisitInfo
import com.samarthhms.models.SwitchAdminData
import com.samarthhms.utils.DateTimeUtils
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Period

class SwitchAdminAdapter internal constructor(var recyclerOnItemViewClickListener: RecyclerOnItemViewClickListener, var admins: List<SwitchAdminData>) : RecyclerView.Adapter<SwitchAdminAdapter.SwitchAdminHolder>() {
    override fun onBindViewHolder(adminHolder: SwitchAdminAdapter.SwitchAdminHolder, position: Int) {
        adminHolder.bind(admins[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SwitchAdminAdapter.SwitchAdminHolder {
        val adminLayoutBinding = SwitchAdminLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SwitchAdminHolder(adminLayoutBinding)
    }

    override fun getItemCount(): Int {
        return admins.size
    }

    inner class SwitchAdminHolder internal constructor(private val adminLayoutBinding: SwitchAdminLayoutBinding) : RecyclerView.ViewHolder(adminLayoutBinding.root) {
        fun bind(switchAdmin: SwitchAdminData) {
            adminLayoutBinding.adminName.text = switchAdmin.admin?.firstName + " " + switchAdmin.admin?.lastName
            adminLayoutBinding.selfTitle.visibility = if(switchAdmin.isAccountOwner) View.VISIBLE else View.GONE
            adminLayoutBinding.switchStatus.visibility = if(switchAdmin.isCurrentUser) View.VISIBLE else View.GONE
            if(!switchAdmin.isCurrentUser){
                adminLayoutBinding.infoBlock.setOnClickListener {
                    recyclerOnItemViewClickListener.onItemClicked(switchAdmin)
                }
            }
        }
    }
}