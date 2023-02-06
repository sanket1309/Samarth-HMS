package com.samarthhms.utils

import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*

class DateTimeUtils {
    companion object{
        fun getLocalDateTime(value: String?): LocalDateTime? {
            if(value == null) return null
            return LocalDateTime.parse(value, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        }

        fun getHoursFrom(localDateTime: LocalDateTime):Long{
            return localDateTime.until(LocalDateTime.now(), ChronoUnit.HOURS)
        }

        fun getTimestamp(localDateTime: LocalDateTime) : Timestamp {
            val instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant()
            val date = Date.from(instant)
            return Timestamp(date)
        }

        fun getLocalDateTime(date: Date): LocalDateTime{
            return LocalDateTime.ofInstant(Instant.ofEpochMilli(date.time), ZoneId.systemDefault())
        }

        fun getLocalDateTime(timestamp: Timestamp) :LocalDateTime{
            val date = timestamp.toDate()
            return getLocalDateTime(date)
        }

        fun getLocalDateTimeFromDate(dateVal: String) : LocalDateTime{
            val date = SimpleDateFormat("dd/MM/yyyy").parse(dateVal)
            return getLocalDateTime(date)
        }

        fun isDateBeforeOrEqualToToday(dateVal: String): Boolean{
            val date = SimpleDateFormat("dd/MM/yyyy").parse(dateVal)
            val currentDate = Date()
            return date <= currentDate
        }
    }
}