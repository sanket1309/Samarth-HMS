package com.samarthhms.utils

import com.samarthhms.constants.Constants
import java.math.BigInteger

class InputFormatterUtils {
    companion object{

        fun formatName(name: String?, firstLetterCapital: Boolean = true): String{
            if(name.isNullOrBlank()){
                return Constants.EMPTY
            }
            val formattedName = name.trim()
            if(firstLetterCapital){
                formattedName.lowercase().replaceFirstChar { it.uppercase() }
            }
            return formattedName
        }

        fun formatContactNumberFromEdittext(number: String?): String{
            if(number.isNullOrBlank()){
                return Constants.EMPTY
            }
            var formattedNumber = number.trim()
            formattedNumber = formattedNumber.lowercase().trim()
            formattedNumber = formattedNumber.replace(Constants.SPACE, Constants.EMPTY)
            return formattedNumber
        }

        fun formatTime(time: String?): String {
            if (time.isNullOrBlank()) {
                return Constants.EMPTY
            }
            var formattedTime = time.trim()
            formattedTime = formattedTime.uppercase()
            return formattedTime
        }

        fun formatAge(age: String?): String {
            if (age.isNullOrBlank()) {
                return Constants.DefaultValues.AGE_TEXT
            }
            return age.trim()
        }

        fun formatNumber(number: String?): String {
            if (number.isNullOrBlank()) {
                return BigInteger.ZERO.toString()
            }
            return number.trim()
        }

        fun formatString(string: String?): String {
            if (string.isNullOrBlank()) {
                return Constants.EMPTY
            }
            return string.trim()
        }
    }
}