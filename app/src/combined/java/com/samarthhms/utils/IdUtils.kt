package com.samarthhms.utils

import android.util.Log
import com.google.firebase.Timestamp
import com.google.type.DateTime
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

        fun incrementYearWiseId(id: String): String {
            try{
                if(!Validation.validateIpdNumber(id)){
                    throw Exception("Invalid ID")
                }
                val serialNumber = id.substring(0,2)
                val yearNumber = id.substring(3)
                return if(yearNumber == DateTimeUtils.getCurrentYear().substring(2)){
                    serialNumber.toInt().plus(1).toString()+"/"+yearNumber
                }
                else{
                    "01/"+yearNumber.toInt().plus(1)
                }
            }catch (e: Exception){
                Log.e("Id_Utils_Error","Error while incrementing year wise id")
                throw e
            }
        }
    }
}