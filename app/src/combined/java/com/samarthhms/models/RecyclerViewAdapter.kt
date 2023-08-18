package com.samarthhms.models

import android.annotation.SuppressLint
import android.os.Parcelable
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.samarthhms.constants.Constants
import com.samarthhms.constants.Gender
import com.samarthhms.ui.BillAdapter
import com.samarthhms.utils.FirestoreLocalDateTime
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

public abstract class RecyclerViewAdapter<VH : RecyclerViewAdapter<VH,D>.ViewHolder,D>(
    protected var data: MutableList<D> = mutableListOf()
): RecyclerView.Adapter<VH>() {
    @SuppressLint("NotifyDataSetChanged")
    fun updateData(data: List<D>){
        this.data.clear()
        this.data.addAll(data.toMutableList())
        notifyDataSetChanged()
    }

    fun appendData(data: D){
        addData(this.data.size, data)
    }

    fun addData(position: Int, data: D){
        this.data.add(position, data)
        notifyItemInserted(position)
    }

    fun deleteItem(position: Int){
        this.data.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun onBindViewHolder(viewHolder: VH, position: Int) {
        viewHolder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    abstract inner class ViewHolder(rootView: View): RecyclerView.ViewHolder(rootView){
        open fun bind(data: D) {}
    }
}