package com.samarthhms.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.DrawableContainer
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.StateListDrawable
import android.widget.EditText
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.google.firebase.Timestamp
import com.samarthhms.R
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*

class InputFieldColorUtils {
    companion object{
        fun changeColorOfInputFields(fieldTitle: TextView, fieldInput: EditText, color: Int, context: Context, resources: Resources) {
            val colorValue = ContextCompat.getColor(context, color)
            fieldTitle.setTextColor(colorValue)
            val fieldInputDrawable = fieldInput.background as StateListDrawable
            val dcs = fieldInputDrawable.constantState as DrawableContainer.DrawableContainerState
            val drawableItem = dcs.children[0] as GradientDrawable
            val pixels = R.dimen.login_edittext_background_stroke_width * resources.displayMetrics.density.toInt()
            drawableItem.setStroke(pixels, colorValue)
        }

        fun changeColorOfInputFields(fieldInput: EditText, color: Int,context: Context){
            val colorValue = ContextCompat.getColor(context, color)
            val fieldInputDrawable = fieldInput.background as StateListDrawable
            val dcs = fieldInputDrawable.constantState as DrawableContainer.DrawableContainerState
            val drawableItem = dcs.children[0] as GradientDrawable
            val pixels = R.dimen.login_edittext_background_stroke_width * context.resources.displayMetrics.density.toInt()
            drawableItem.setStroke(pixels, colorValue)
        }
    }
}