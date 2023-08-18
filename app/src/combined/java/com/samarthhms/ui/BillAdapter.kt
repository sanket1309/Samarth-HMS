package com.samarthhms.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import com.samarthhms.databinding.BillItemLayoutBinding
import com.samarthhms.models.BillItem
import com.samarthhms.models.RecyclerViewAdapter
import com.samarthhms.utils.TextChangeListenerUtils
import com.samarthhms.utils.UiDataDisplayUtils
import com.samarthhms.utils.UiDataExtractorUtils

class BillAdapter internal constructor(var generateBillFragment: OnUpdateBillSumListener,var billItems: MutableList<BillItem> = mutableListOf<>()) : RecyclerViewAdapter<BillAdapter.BillItemHolder, BillItem>(billItems) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BillAdapter.BillItemHolder {
        val billItemLayoutBinding = BillItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BillItemHolder(billItemLayoutBinding)
    }

    inner class BillItemHolder internal constructor(val billItemLayoutBinding: BillItemLayoutBinding)
        : RecyclerViewAdapter<BillAdapter.BillItemHolder, BillItem>.ViewHolder(billItemLayoutBinding.root) {

        override fun bind(billItem: BillItem) {
            UiDataDisplayUtils.displayBillItem(billItemLayoutBinding, billItem, true)
            updateSum(true)
            val isNewlyAdded = false
            billItemLayoutBinding.deleteButton.setOnClickListener{
                deleteItem(absoluteAdapterPosition)
                val currentBillItem = UiDataExtractorUtils.extractData(billItemLayoutBinding)
                generateBillFragment.update(-currentBillItem.rate*currentBillItem.quantity)
            }
            billItemLayoutBinding.rate.addTextChangedListener(TextChangeListenerUtils.getTextWatcher(onAfterTC = {updateSum(isNewlyAdded)}))
            billItemLayoutBinding.quantity.addTextChangedListener(TextChangeListenerUtils.getTextWatcher(onAfterTC = {updateSum(isNewlyAdded)}))
        }

        private fun updateSum(isNewlyAdded: Boolean){
            UiDataDisplayUtils.updateBillItemSum(billItemLayoutBinding, generateBillFragment, isNewlyAdded)
        }
    }
}