package com.samarthhms.utils

import com.samarthhms.constants.Constants
import com.samarthhms.constants.Gender
import com.samarthhms.models.Location
import com.samarthhms.models.Name
import com.samarthhms.models.Patient
import java.math.BigInteger
import java.time.LocalDateTime
import java.util.*

class OutputFormatterUtils {
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

        fun formatFullName(name: Name?, includeMiddleName: Boolean = false): String{
            val firstName = formatName(name?.firstName)
            val middleName = formatName(name?.middleName)
            val lastName = formatName(name?.lastName)
            return if(includeMiddleName) firstName + Constants.SPACE + middleName + Constants.SPACE + lastName
            else firstName + Constants.SPACE + lastName
        }

        fun formatGenderForPatientItem(gender: String?): String{
            var result = gender
            if(gender.isNullOrBlank()){
                result = Gender.NONE.value
            }
            return String.format("%s, ",result)
        }

        fun formatYearWiseId(id: String?): String{
            if(Objects.isNull(id)){
                return Constants.DefaultValues.ID
            }
            return StringUtils.formatYearWiseIdSpacePadded(id!!)
        }

        private fun formatAgeFromDateOfBirth(dob: LocalDateTime?): String{
            if(Objects.isNull(dob)){
                return Constants.DefaultValues.AGE_TEXT
            }
            return DateTimeUtils.getDurationTillNowInYearsAndMonths(dob!!)
        }

        fun formatPatientAgeForListItem(patient: Patient?): String{
            if(Objects.isNull(patient)){
                return Constants.DefaultValues.AGE_TEXT
            }
            if(Objects.isNull(patient!!.dateOfBirth)){
                return patient.ageInText!!
            }
            return formatAgeFromDateOfBirth(patient.dateOfBirth)
        }

        fun formatLocationForListItem(location: Location?): String{
            if(Objects.isNull(location)){
                return Constants.EMPTY
            }
            return String.format("%s, Tal. %s",location!!.town, location.taluka)
        }

        fun formatContactNumberForEditText(number: String?): String{
            var formattedNumber = number
            if(!Validation.validateContactNumber(number)){
                formattedNumber = Constants.DefaultValues.CONTACT_NUMBER
            }
            formattedNumber = String.format("%s %s %s",
                formattedNumber!!.substring(0,3),
                formattedNumber.substring(3,6),
                formattedNumber.substring(6,10),
            )
            return formattedNumber
        }

        fun formatDateForEdittext(localDateTime: LocalDateTime?): String {
            if (Objects.isNull(localDateTime)) {
                return Constants.EMPTY
            }
            return DateTimeUtils.getDateFormat(localDateTime!!)
        }

        fun formatDate(localDateTime: LocalDateTime?): String {
            if (Objects.isNull(localDateTime)) {
                return Constants.EMPTY
            }
            return DateTimeUtils.getDateWithMonthInWords(localDateTime!!)
        }

        fun formatTime(localDateTime: LocalDateTime?): String {
            if (Objects.isNull(localDateTime)) {
                return Constants.EMPTY
            }
            return DateTimeUtils.getTime(localDateTime!!)
        }

        fun formatString(string: String?): String {
            if (string.isNullOrBlank()) {
                return Constants.EMPTY
            }
            return string.trim()
        }

        fun formatNumber(number: Float?): String {
//            if (number()) {
//                return BigInteger.ZERO.toString()
//            }
            return number.toString()
        }

        fun formatNumber(number: Int?): String {
//            if (number()) {
//                return BigInteger.ZERO.toString()
//            }
            return number.toString()
        }
    }
}