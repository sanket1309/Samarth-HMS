package com.samarthhms.utils

import android.content.Context
import android.content.DialogInterface
import android.util.Log
import android.widget.Filterable
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.samarthhms.R
import java.time.Duration
import java.util.Objects

class DialogUtils {
    companion object{
        fun<T> popBottomSheetDialog(adapter: T, context: Context):BottomSheetDialog
        where T: RecyclerView.Adapter<out RecyclerView.ViewHolder>, T:Filterable{
            val bottomSheetDialog = context.let { it -> BottomSheetDialog(it) }
            bottomSheetDialog.setContentView(R.layout.bottom_sheet_layout)
            val recyclerView = bottomSheetDialog.findViewById<RecyclerView>(R.id.recycler_view)
            recyclerView?.adapter = adapter
            val searchView = bottomSheetDialog.findViewById<SearchView>(R.id.search_view)
            searchView?.setOnQueryTextListener(TextChangeListenerUtils.getQueryListener(onQTChange = {adapter.filter.filter(it)}))
            searchView?.isFocusable = true
            searchView?.isIconified = false
            searchView?.requestFocusFromTouch()
            bottomSheetDialog.show()
            return bottomSheetDialog
        }

        fun popDialogWithMessageOnly(context: Context, message: String) {
            val alertDialogBuilder = AlertDialog.Builder(context)
            alertDialogBuilder.setMessage(message)
                .setCancelable(true)
                .show()
        }

        fun popDialog(context: Context, onPositive: (()->Unit)?, onNegative: (()->Unit)? = null,message: String, positiveMessage: String, negativeMessage: String) {
            val dialogClickListener = getListener(onPositive, onNegative)
            val alertDialogBuilder = AlertDialog.Builder(context)
            alertDialogBuilder.setMessage(message)
                .setPositiveButton(positiveMessage, dialogClickListener)
                .setNegativeButton(negativeMessage, dialogClickListener)
                .show()
        }

        private fun getListener(onPositive: (()->Unit)? = null, onNegative: (()->Unit)? = null): DialogInterface.OnClickListener {
            return DialogInterface.OnClickListener{
                    dialog, which ->
                when(which){
                    DialogInterface.BUTTON_POSITIVE -> { onPositive?.let { onPositive() } }
                    DialogInterface.BUTTON_NEGATIVE -> {
                        onNegative?.let { onNegative() }
                        dialog.dismiss()
                    }
                }
            }
        }
    }
}