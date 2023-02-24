package com.samarthhms.utils

class StringUtils {
    companion object{
        fun getResultFoundText(resultCount: Int): String {
            return when(resultCount){
                0 -> "No Result Found"
                1 -> " 1 Result Found"
                else -> " $resultCount Results Found"
            }
        }

        fun formatName(name: String): String {
            if (name.isBlank()) return name
            return name.lowercase().replaceFirstChar { it.uppercase() }
        }

        fun formatPhoneNumberForEditText(phoneNumber: String): String {
            return phoneNumber.substring(0,3)+" "+phoneNumber.substring(3,6)+" "+phoneNumber.substring(6)
        }
    }
}