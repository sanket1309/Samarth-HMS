package com.samarthhms.staff.utils

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

        fun getHours(): Int {
            return DateTimeFormatter.ofPattern("HH").format(LocalDateTime.now()).toInt()
        }

        fun getDay(): String {
            return DateTimeFormatter.ofPattern("EEEE").format(LocalDateTime.now()).toString()
        }

        fun getDate(): String {
            return DateTimeFormatter.ofPattern("MMM dd").format(LocalDateTime.now()).toString()
        }

        fun getCurrentYear(): String {
            return DateTimeFormatter.ofPattern("yyyy").format(LocalDateTime.now())
        }

        fun getLocalDateTime(date: String?, time: String?): LocalDateTime? {
            if(date == null || time == null) return null
            return LocalDateTime.parse(date+" "+time.lowercase(), DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm a"))
        }

        fun getDate(localDateTime: LocalDateTime): String {
            return DateTimeFormatter.ofPattern("ddMMyyyy").format(localDateTime)
        }

        fun getDateFormat(localDateTime: LocalDateTime): String {
            return DateTimeFormatter.ofPattern("d/MM/yyyy").format(localDateTime)
        }

        fun getDateTime(localDateTime: LocalDateTime): String {
            return DateTimeFormatter.ofPattern("d/M/yyyy hh:mm a").format(localDateTime)
                .uppercase(Locale.getDefault())
        }

        fun getDurationTillNowInYearsAndMonths(localDateTime: LocalDateTime): String {
            return DateTimeFormatter.ofPattern("ddMMyyyy").format(localDateTime)
        }

        fun getStartOfDate(date: LocalDateTime): LocalDateTime {
            return date.toLocalDate().atStartOfDay()
        }

        fun getEndOfDate(date: LocalDateTime): LocalDateTime {
            return date.toLocalDate().atStartOfDay().plusDays(1)
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
            val time = LocalDateTime.ofInstant(Instant.ofEpochMilli(date.time), ZoneId.systemDefault())
//            Log.d("DEBUG_STAFF","LOCALDATETIME : $time")
            return time
        }

        fun getLocalDateTime(timestamp: Timestamp) :LocalDateTime{
//            Log.d("DEBUG_STAFF", "TIMESTAMP FB DATE : ${timestamp.toDate()}")
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