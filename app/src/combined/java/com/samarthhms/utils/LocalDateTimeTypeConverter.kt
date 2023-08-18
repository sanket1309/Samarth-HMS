package com.samarthhms.utils

import android.annotation.SuppressLint
import androidx.room.TypeConverter
import com.google.firebase.Timestamp
import java.sql.Time
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*

annotation class FirestoreLocalDateTime
class LocalDateTimeTypeConverter {
    @TypeConverter
    fun fromLocalDateTime(localDateTime: LocalDateTime?): Timestamp? {
        return localDateTime?.let {
            DateTimeUtils.getTimestamp(localDateTime)
        }
    }

    @TypeConverter
    fun toLocalDateTime(timestamp: Timestamp?): LocalDateTime? {
        return timestamp?.let {
            DateTimeUtils.getLocalDateTime(timestamp)
        }
    }
}