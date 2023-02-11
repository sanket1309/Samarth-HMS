package com.samarthhms.utils

import android.util.Log
import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*

class IdUtils {
    companion object{

        val PATIENT_ID_PREFIX = "SBP"
        val PATIENT_ID_SUFFIX_LENGTH = 5

        fun incrementPatientId(patientId: String): String {
            if(patientId.isBlank()) return patientId
            try{
                val suffixNumber = patientId.removePrefix(PATIENT_ID_PREFIX).toInt() + 1
                return PATIENT_ID_PREFIX + suffixNumber.toString().padStart(PATIENT_ID_SUFFIX_LENGTH, '0')
            }catch (e: Exception){
                Log.e("Id_Utils_Error","Error while incrementing patient id")
                throw e
            }
        }

        fun decrementPatientId(patientId: String): String {
            if(patientId.isBlank()) return patientId
            try{
                var suffixNumber = patientId.removePrefix(PATIENT_ID_PREFIX).toInt()
                if(suffixNumber == 1){
                    throw Exception("Cannot decrement patient id to zero")
                }
                suffixNumber--
                return PATIENT_ID_PREFIX + suffixNumber.toString().padStart(PATIENT_ID_SUFFIX_LENGTH, '0')
            }catch (e: Exception){
                Log.e("Id_Utils_Error","Error while decrementing patient id")
                throw e
            }
        }


    }
}