package com.samarthhms.ui

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Timestamp
import com.samarthhms.R
import com.samarthhms.constants.Constants
import com.samarthhms.databinding.VisitInfoLayoutBinding
import com.samarthhms.models.PatientVisitInfo
import com.samarthhms.models.RecyclerViewAdapter
import com.samarthhms.utils.DateTimeUtils
import com.samarthhms.utils.UiDataDisplayUtils
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Period

class VisitInfoAdapter internal constructor(var context: Context?,var recyclerOnItemViewClickListener: RecyclerOnItemViewClickListener, var patientsToday: List<PatientVisitInfo>)
    : RecyclerViewAdapter<VisitInfoAdapter.VisitInfoHolder, PatientVisitInfo>(patientsToday.toMutableList()) {

    override fun getItemViewType(position: Int): Int {
        return (itemCount-position-1) % Constants.Drawables.LIST_ITEM_BACKGROUNDS.size
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VisitInfoAdapter.VisitInfoHolder {
        val visitInfoLayoutBinding = VisitInfoLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        visitInfoLayoutBinding.infoBlock.background = context?.resources?.getDrawable(Constants.Drawables.LIST_ITEM_BACKGROUNDS[viewType],null)
        return VisitInfoHolder(visitInfoLayoutBinding)
    }

    inner class VisitInfoHolder internal constructor(private val visitInfoLayoutBinding: VisitInfoLayoutBinding)
        : RecyclerViewAdapter<VisitInfoAdapter.VisitInfoHolder, PatientVisitInfo>.ViewHolder(visitInfoLayoutBinding.root) {

        override fun bind(patientVisitInfo: PatientVisitInfo) {
            UiDataDisplayUtils.displayPatientVisitInfo(visitInfoLayoutBinding.root, patientVisitInfo)
            visitInfoLayoutBinding.infoBlock.setOnClickListener{ recyclerOnItemViewClickListener.onItemClicked(patientVisitInfo.patient) }
        }
    }
}