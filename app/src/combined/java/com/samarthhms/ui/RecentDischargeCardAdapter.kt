package com.samarthhms.ui

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.samarthhms.R
import com.samarthhms.constants.Constants
import com.samarthhms.databinding.DischargeCardInfoLayoutBinding
import com.samarthhms.models.DischargeCard
import com.samarthhms.models.RecyclerViewAdapter
import com.samarthhms.utils.StringUtils
import com.samarthhms.utils.UiDataDisplayUtils

class RecentDischargeCardAdapter internal constructor(var context: Context?, var recyclerOnItemViewClickListener: RecyclerOnItemViewClickListener, var dischargeCards: List<DischargeCard> = listOf())
    : RecyclerViewAdapter<RecentDischargeCardAdapter.DischargeCardHolder, DischargeCard>(dischargeCards.toMutableList()) {

    override fun getItemViewType(position: Int): Int {
        return (itemCount-position-1) % Constants.Drawables.LIST_ITEM_BACKGROUNDS.size
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DischargeCardHolder {
        val dischargeCardInfoLayoutBinding = DischargeCardInfoLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        dischargeCardInfoLayoutBinding.infoBlock.background = context?.resources?.getDrawable(Constants.Drawables.LIST_ITEM_BACKGROUNDS[viewType],null)
        return DischargeCardHolder(dischargeCardInfoLayoutBinding)
    }

    inner class DischargeCardHolder internal constructor(private val dischargeCardInfoLayoutBinding: DischargeCardInfoLayoutBinding)
        : RecyclerViewAdapter<RecentDischargeCardAdapter.DischargeCardHolder, DischargeCard>.ViewHolder(dischargeCardInfoLayoutBinding.root) {
        override fun bind(dischargeCard: DischargeCard) {
            UiDataDisplayUtils.displayDischargeCardListItem(dischargeCardInfoLayoutBinding.root, dischargeCard)
            dischargeCardInfoLayoutBinding.infoBlock.setOnClickListener{ recyclerOnItemViewClickListener.onItemClicked(dischargeCard) }
        }
    }
}