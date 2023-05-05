package com.samarthhms.staff.ui

import android.view.View

interface RecyclerOnItemViewClickListener {
    fun onItemClicked(data: Any?, requester: String = "")
}