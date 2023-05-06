package com.samarthhms.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.samarthhms.databinding.SwitchAdminLayoutBinding
import com.samarthhms.models.AdminDetails

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
        @SuppressLint("SetTextI18n")
        fun bind(adminDetails: AdminDetails) {
            val admin = adminDetails.admin
            val switchAdmin = adminDetails.switchAdminData!!
            adminLayoutBinding.adminName.text = admin.firstName + " " + admin.lastName
            adminLayoutBinding.selfTitle.visibility = if(switchAdmin.isAccountOwner) View.VISIBLE else View.GONE
            adminLayoutBinding.switchStatus.visibility = if(switchAdmin.isCurrentUser) View.VISIBLE else View.GONE
            adminLayoutBinding.infoBlock.setOnClickListener {
                recyclerOnItemViewClickListener.onItemClicked(adminDetails)
            }
        }
    }
}