package com.samarthhms.utils

import android.annotation.SuppressLint
import com.google.firebase.Timestamp
import java.sql.Time
import java.text.SimpleDateFormat
import java.time.Duration
import java.time.Instant
import java.time.LocalDateTime
import java.time.Period
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*

class DateTimeUtils {
    companion object{
        fun getLocalDateTime(value: String?): LocalDateTime? {
            if(value.isNullOrBlank()) return null
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
            return LocalDateTime.parse(date+" "+time.uppercase(), DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm a"))
        }

        fun getDate(localDateTime: LocalDateTime): String {
            return DateTimeFormatter.ofPattern("ddMMyyyy").format(localDateTime)
        }

        fun getDateFormat(localDateTime: LocalDateTime): String {
            return DateTimeFormatter.ofPattern("dd/MM/yyyy").format(localDateTime)
        }

        fun getTime(localDateTime: LocalDateTime): String {
            return DateTimeFormatter.ofPattern("hh:mm a").format(localDateTime)
                .uppercase(Locale.getDefault())
        }

        fun getDateTime(localDateTime: LocalDateTime): String {
            return DateTimeFormatter.ofPattern("dd/M/yyyy hh:mm a").format(localDateTime)
                .uppercase(Locale.getDefault())
        }

        fun getDurationTillNowInYearsAndMonths(localDateTime: LocalDateTime): String {
            val duration = Period.between(localDateTime.toLocalDate(), LocalDateTime.now().toLocalDate())
            return String.format("%dY %dM",duration.years, duration.months)
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

        private fun getLocalDateTime(date: Date?): LocalDateTime? {
            if(date == null) return null
            return LocalDateTime.ofInstant(Instant.ofEpochMilli(date.time), ZoneId.systemDefault())
        }

        fun getLocalDateTime(timestamp: Timestamp) :LocalDateTime?{
            val date = timestamp.toDate()
            return getLocalDateTime(date)
        }

        @SuppressLint("SimpleDateFormat")
        fun <T> getTimestampFromDate(dateVal: String, clazz: Class<T>) : T?{

            if(clazz == Timestamp::class.java){
                return null
            }else if(clazz == LocalDateTime::class.java){
                val date = SimpleDateFormat("dd/MM/yyyy").parse(dateVal) ?: return null
                getLocalDateTime(date) as T?
            }
            return null
        }

        @SuppressLint("SimpleDateFormat")
        fun getLocalDateTimeFromDate(dateVal: String) : LocalDateTime?{
            val date = SimpleDateFormat("dd/MM/yyyy").parse(dateVal) ?: return null
            return getLocalDateTime(date)
        }

        @SuppressLint("SimpleDateFormat")
        fun isDateBeforeOrEqualToToday(dateVal: String): Boolean{
            val date = SimpleDateFormat("dd/MM/yyyy").parse(dateVal)
            val currentDate = Date()
            return date <= currentDate
        }
    }
}