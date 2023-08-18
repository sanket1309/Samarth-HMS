package com.samarthhms.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.samarthhms.databinding.SwitchAdminLayoutBinding
import com.samarthhms.models.Admin
import com.samarthhms.models.AdminDetails
import com.samarthhms.models.RecyclerViewAdapter
import com.samarthhms.utils.UiDataDisplayUtils

class AdminDetailsAdapter internal constructor(var recyclerOnItemViewClickListener: RecyclerOnItemViewClickListener, var admins: List<AdminDetails> = listOf()) : RecyclerViewAdapter<AdminDetailsAdapter.AdminHolder, AdminDetails>(admins.toMutableList()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdminDetailsAdapter.AdminHolder {
        val adminLayoutBinding = SwitchAdminLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AdminHolder(adminLayoutBinding)
    }

    inner class AdminHolder internal constructor(private val adminLayoutBinding: SwitchAdminLayoutBinding)
        : RecyclerViewAdapter<AdminDetailsAdapter.AdminHolder, AdminDetails>.ViewHolder(adminLayoutBinding.root) {
        @SuppressLint("SetTextI18n")
        override fun bind(adminDetails: AdminDetails) {
            UiDataDisplayUtils.displayAdminDetails(adminLayoutBinding.root, adminDetails)
            adminLayoutBinding.infoBlock.setOnClickListener {
                recyclerOnItemViewClickListener.onItemClicked(adminDetails)
            }
        }
    }
}