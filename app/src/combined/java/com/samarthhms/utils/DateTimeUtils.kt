package com.samarthhms.utils

import com.google.firebase.Timestamp
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

        fun getLocalDateTime(timestamp: Timestamp) :LocalDateTime{
            val date = timestamp.toDate()
            return LocalDateTime.ofInstant(Instant.ofEpochMilli(date.time), ZoneId.systemDefault())
        }
    }
}