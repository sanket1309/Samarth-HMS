package com.samarthhms.utils

import android.content.Context
import android.util.Log
import android.widget.Toast
import java.time.Duration

class ToastUtils {
    companion object{
        fun showToast(context: Context, message: String) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }
}