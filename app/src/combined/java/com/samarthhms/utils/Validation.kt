package com.samarthhms.utils


class Validation {
    companion object{
        fun validatePatientId(patientId: String):Boolean{
            if(patientId.substring(0,3) != IdUtils.PATIENT_ID_PREFIX){
                return false
            }
            if(patientId.length != 8){
                return false
            }
            return patientId.substring(3).matches(Regex("^[0-9]*$"))
        }

        fun validateIpdNumber(ipdNumber: String):Boolean{
            return ipdNumber.matches(Regex("^[0-9]{2}[\\/][0-9]{2}$"))
        }

        fun validateTime(time: String):Boolean{
            return time.matches(Regex("^(1[0-2]|0[1-9]):([0-5][0-9]) ([AP][M]|[ap][m])$"))
        }

        fun validateUserName(userName: String):Boolean{
            return true
        }

        fun validatePassword(password: String):Boolean{
            return true
        }

        fun validateName(name: String?):Boolean{
            if(name.isNullOrBlank()){
                return false
            }
            return name.matches(Regex("^[a-zA-Z]*$"))
        }

        fun validateContactNumber(contactNumber: String?):Boolean{
            if(contactNumber.isNullOrBlank()){
                return false
            }
            return contactNumber.matches(Regex("^[6-9][0-9]{9}\$"))
        }

        fun validateDate(date: String?):Boolean{
            if(date.isNullOrBlank()){
                return false
            }
            if(!date.matches(Regex("^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[13-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})\$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))\$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})\$"))){
                return false
            }
            return true
        }
    }
}