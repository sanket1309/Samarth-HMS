package com.samarthhms.utils

import pl.allegro.finance.tradukisto.ValueConverters

class StringUtils {
    companion object{

        fun getShowingBillsText(resultCount: Int): String {
            return when(resultCount){
                0 -> "No bills to show"
                1 -> "Showing 1 bill"
                else -> "Showing $resultCount bills"
            }
        }

        fun getShowingDischargeCardsText(resultCount: Int): String {
            return when(resultCount){
                0 -> "No discharge cards to show"
                1 -> "Showing 1 discharge card"
                else -> "Showing $resultCount discharge cards"
            }
        }

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

        fun formatYearWiseId(id: String): String {
            return id.padStart(6, '0')
        }

        fun formatYearWiseIdGeneral(id: String): String {
            var result = id
            if(id.length == 6 && id[0] =='0'){
                result = id.substring(1)
            }
            return result
        }

        fun formatYearWiseIdSpacePadded(id: String): String {
            var result = id
            if(id.length == 6 && id[0] =='0'){
                result = id.substring(1)
            }
            return result.padStart(6, ' ')
        }

        fun formatYearWiseIdForFirebase(id: String): String {
            var result = id.split("/")
            return result[0]+"-"+result[1]
        }

        fun formatYearWiseIdFromFirebaseId(id: String): String {
            var result = id.split("-")
            return result[0]+"/"+result[1]
        }
    }
}