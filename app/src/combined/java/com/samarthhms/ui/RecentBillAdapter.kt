package com.samarthhms.ui

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.samarthhms.R
import com.samarthhms.constants.Constants
import com.samarthhms.databinding.BillInfoLayoutBinding
import com.samarthhms.models.Bill
import com.samarthhms.models.RecyclerViewAdapter
import com.samarthhms.utils.StringUtils
import com.samarthhms.utils.UiDataDisplayUtils

class RecentBillAdapter internal constructor(var context: Context?, var recyclerOnItemViewClickListener: RecyclerOnItemViewClickListener, var bills: List<Bill> = listOf())
    : RecyclerViewAdapter<RecentBillAdapter.BillHolder, Bill>(bills.toMutableList()) {

    override fun getItemViewType(position: Int): Int {
        return (itemCount-position-1) % Constants.Drawables.LIST_ITEM_BACKGROUNDS.size
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentBillAdapter.BillHolder {
        val billInfoLayoutBinding = BillInfoLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        billInfoLayoutBinding.infoBlock.background = context?.resources?.getDrawable(Constants.Drawables.LIST_ITEM_BACKGROUNDS[viewType],null)
        return BillHolder(billInfoLayoutBinding)
    }

    inner class BillHolder internal constructor(private val billInfoLayoutBinding: BillInfoLayoutBinding)
        : RecyclerViewAdapter<RecentBillAdapter.BillHolder, Bill>.ViewHolder(billInfoLayoutBinding.root) {

        override fun bind(bill: Bill) {
            UiDataDisplayUtils.displayBillListItem(billInfoLayoutBinding.root, bill)
            billInfoLayoutBinding.infoBlock.setOnClickListener{ recyclerOnItemViewClickListener.onItemClicked(bill) }
        }
    }
}