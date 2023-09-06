package com.samarthhms.utils

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.samarthhms.models.RecyclerViewAdapter
import java.util.*

class RecyclerViewAdapterUtils {
    companion object{
        @SuppressLint("NotifyDataSetChanged")
        fun<VH : RecyclerViewAdapter<VH,D>.ViewHolder,D> updateData(adapter: Adapter<*>?, data: List<D>) {
            (adapter as RecyclerViewAdapter<VH, D>).updateData(data)
        }

        fun<VH : RecyclerViewAdapter<VH,D>.ViewHolder,D> appendData(adapter: Adapter<*>?, data: D) {
            (adapter as RecyclerViewAdapter<VH, D>).appendData(data)
        }
    }
}