package com.samarthhms.utils

class StringUtils {
    companion object{
        fun formatName(name: String): String {
            if (name.isBlank()) return name
            return name.lowercase().replaceFirstChar { it.uppercase() }
        }

        fun formatPhoneNumberForEditText(phoneNumber: String): String {
            return phoneNumber.substring(0,3)+" "+phoneNumber.substring(3,6)+" "+phoneNumber.substring(6)
        }
    }
}