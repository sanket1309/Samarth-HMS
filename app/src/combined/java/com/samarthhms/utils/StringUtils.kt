package com.samarthhms.utils

import pl.allegro.finance.tradukisto.ValueConverters

class StringUtils {
    companion object{
        fun getResultFoundText(resultCount: Int): String {
            return when(resultCount){
                0 -> "No Result Found"
                1 -> " 1 Result Found"
                else -> " $resultCount Results Found"
            }
        }

        fun getPatientCountText(resultCount: Int): String {
            return when(resultCount){
                0 -> "No Patient"
                1 -> " 1 Patient"
                else -> " $resultCount Results"
            }
        }

        fun formatName(name: String): String {
            if (name.isBlank()) return name
            return name.lowercase().replaceFirstChar { it.uppercase() }
        }

        fun formatPhoneNumberForEditText(phoneNumber: String): String {
            return phoneNumber.substring(0,3)+" "+phoneNumber.substring(3,6)+" "+phoneNumber.substring(6)
        }

        fun formatPrice(price: Int): String{
            var p=price.toString()
            var formattedPrice=price.toString()
            if(p.length>3){
                formattedPrice = formattedPrice.substring(0,formattedPrice.length-3)+","+formattedPrice.substring(formattedPrice.length-3)
            }
            if(p.length>5){
                formattedPrice = formattedPrice.substring(0,formattedPrice.length-6)+","+formattedPrice.substring(formattedPrice.length-6)
            }
            return formattedPrice
        }

        fun getAmountInWords(amount: Int): String{
            val converters = ValueConverters.ENGLISH_INTEGER
            return converters.asWords(amount).uppercase()
        }
    }
}