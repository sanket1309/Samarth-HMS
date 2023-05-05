package com.samarthhms.ui

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.samarthhms.databinding.BillItemLayoutBinding
import com.samarthhms.models.BillItem

class BillAdapter internal constructor(var generateBillFragment: OnUpdateBillSumListener,var billItems: MutableList<BillItem>) : RecyclerView.Adapter<BillAdapter.BillItemHolder>() {
    override fun onBindViewHolder(medicineTemplateHolder: BillAdapter.BillItemHolder, position: Int) {
        medicineTemplateHolder.bind(billItems[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BillAdapter.BillItemHolder {
        val billItemLayoutBinding = BillItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BillItemHolder(billItemLayoutBinding)
    }

    override fun getItemCount(): Int {
        return billItems.size
    }

    inner class BillItemHolder internal constructor(val billItemLayoutBinding: BillItemLayoutBinding) : RecyclerView.ViewHolder(billItemLayoutBinding.root) {

        var isNewlyAdded = false

        fun bind(billItem: BillItem) {
            billItemLayoutBinding.itemName.setText(if(billItem.itemName == "DEFAULT") "" else billItem.itemName)
            isNewlyAdded = true
            billItemLayoutBinding.rate.setText(billItem.rate.toString())
            billItemLayoutBinding.quantity.setText(billItem.quantity.toString())
            billItemLayoutBinding.sum.setText((billItem.quantity * billItem.rate).toString())
            updateSum()
            isNewlyAdded = false
            billItemLayoutBinding.deleteButton.setOnClickListener{
                billItems.removeAt(absoluteAdapterPosition)
                notifyItemRemoved(absoluteAdapterPosition)
                val rate = if(billItemLayoutBinding.rate.text.isBlank()) 0 else billItemLayoutBinding.rate.text.toString().toInt()
                val quantity = if(billItemLayoutBinding.quantity.text.isBlank()) 0 else billItemLayoutBinding.quantity.text.toString().toInt()
                generateBillFragment.update(-rate*quantity)
            }
            billItemLayoutBinding.rate.addTextChangedListener(object: TextWatcher{
                override fun beforeTextChanged( s: CharSequence?, start: Int, count: Int, after: Int) {
//                    TODO("Not yet implemented")
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                    TODO("Not yet implemented")
                }

                override fun afterTextChanged(s: Editable?) {
                    updateSum()
                }
            })

            billItemLayoutBinding.quantity.addTextChangedListener(object: TextWatcher{
                override fun beforeTextChanged( s: CharSequence?, start: Int, count: Int, after: Int) {
//                    TODO("Not yet implemented")
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                    TODO("Not yet implemented")
                }

                override fun afterTextChanged(s: Editable?) {
                    updateSum()
                }
            })
        }

        fun updateSum(){
            val rate = if(billItemLayoutBinding.rate.text.isBlank()) 0 else billItemLayoutBinding.rate.text.toString().toInt()
            val quantity = if(billItemLayoutBinding.quantity.text.isBlank()) 0 else billItemLayoutBinding.quantity.text.toString().toInt()
            var previousSum = if(billItemLayoutBinding.sum.text.isBlank()) 0 else billItemLayoutBinding.sum.text.toString().toInt()
            val sum = rate * quantity
            billItemLayoutBinding.sum.setText(sum.toString())
            if(isNewlyAdded) previousSum = 0
            generateBillFragment.update(sum-previousSum)
        }
    }
}