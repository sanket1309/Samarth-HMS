package com.samarthhms.ui

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Timestamp
import com.samarthhms.R
import com.samarthhms.constants.Constants
import com.samarthhms.databinding.VisitInfoDateLayoutBinding
import com.samarthhms.databinding.VisitInfoLayoutBinding
import com.samarthhms.models.PatientVisitInfo
import com.samarthhms.models.RecyclerViewAdapter
import com.samarthhms.utils.DateTimeUtils
import com.samarthhms.utils.UiDataDisplayUtils
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Period

class VisitInfoDateAdapter internal constructor(var context: Context?, var recyclerOnItemViewClickListener: RecyclerOnItemViewClickListener, var patientsToday: List<PatientVisitInfo>)
    : RecyclerViewAdapter<VisitInfoDateAdapter.VisitInfoHolder, PatientVisitInfo>(patientsToday.toMutableList()) {

    override fun getItemViewType(position: Int): Int {
        return (itemCount-position-1) % Constants.Drawables.LIST_ITEM_BACKGROUNDS.size
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VisitInfoDateAdapter.VisitInfoHolder {
        val visitInfoDateLayoutBinding = VisitInfoDateLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        visitInfoDateLayoutBinding.infoBlock.background = context?.resources?.getDrawable(Constants.Drawables.LIST_ITEM_BACKGROUNDS[viewType],null)
        return VisitInfoHolder(visitInfoDateLayoutBinding)
    }

    inner class VisitInfoHolder internal constructor(private val visitInfoDateLayoutBinding: VisitInfoDateLayoutBinding)
        : RecyclerViewAdapter<VisitInfoDateAdapter.VisitInfoHolder, PatientVisitInfo>.ViewHolder(visitInfoDateLayoutBinding.root) {

        override fun bind(patientVisitInfo: PatientVisitInfo) {
            UiDataDisplayUtils.displayPatientVisitInfo(visitInfoDateLayoutBinding.root, patientVisitInfo, displayDate = true)
            visitInfoDateLayoutBinding.infoBlock.setOnClickListener{ recyclerOnItemViewClickListener.onItemClicked(patientVisitInfo.patient) }
        }
    }
}