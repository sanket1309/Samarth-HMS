package com.samarthhms.ui

interface RecyclerOnItemViewClickListener {
    fun onItemClicked(data: Any?, requester: String = "", isLongPress: Boolean=false)
}