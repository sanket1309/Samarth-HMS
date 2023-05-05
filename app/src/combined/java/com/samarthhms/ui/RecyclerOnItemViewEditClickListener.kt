package com.samarthhms.ui

interface RecyclerOnItemViewEditClickListener {
    fun onEditClicked(data: Any)
    fun onSaveClicked(data: Any)
    fun onInvalidData(message: String)
    fun onDeleteClicked(data: Any)
}