package com.samarthhms.utils

import android.annotation.SuppressLint
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.firebase.Timestamp
import com.samarthhms.models.RecyclerViewAdapter
import java.sql.Time
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*

class RecyclerViewAdapterUtils {
    companion object{
        @SuppressLint("NotifyDataSetChanged")
        fun<VH : ViewHolder,D> updateData(adapter: Adapter<*>?, data: List<D>) {
            (adapter as RecyclerViewAdapter<VH,D>).updateData(data)
        }
    }
}