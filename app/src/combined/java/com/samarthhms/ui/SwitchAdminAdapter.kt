package com.samarthhms.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.samarthhms.databinding.SwitchAdminLayoutBinding
import com.samarthhms.models.RecyclerViewAdapter
import com.samarthhms.models.SwitchAdminData
import com.samarthhms.utils.UiDataDisplayUtils

class SwitchAdminAdapter internal constructor(var recyclerOnItemViewClickListener: RecyclerOnItemViewClickListener, var admins: List<SwitchAdminData>)
    : RecyclerViewAdapter<SwitchAdminAdapter.SwitchAdminHolder, SwitchAdminData>(admins.toMutableList()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SwitchAdminAdapter.SwitchAdminHolder {
        val adminLayoutBinding = SwitchAdminLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SwitchAdminHolder(adminLayoutBinding)
    }

    inner class SwitchAdminHolder internal constructor(private val adminLayoutBinding: SwitchAdminLayoutBinding)
        : RecyclerViewAdapter<SwitchAdminAdapter.SwitchAdminHolder, SwitchAdminData>.ViewHolder(adminLayoutBinding.root) {

        override fun bind(switchAdmin: SwitchAdminData) {
            UiDataDisplayUtils.displaySwitchAdmin(adminLayoutBinding.root, switchAdmin)
            if(!switchAdmin.isCurrentUser){
                adminLayoutBinding.infoBlock.setOnClickListener { recyclerOnItemViewClickListener.onItemClicked(switchAdmin) }
            }
        }
    }
}