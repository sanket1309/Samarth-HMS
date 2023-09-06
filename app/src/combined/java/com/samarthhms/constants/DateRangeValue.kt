package com.samarthhms.constants

enum class DateRangeValue (val value: String) {
    TODAY("Today"),
    YESTERDAY("Yesterday"),
    THIS_WEEK("This week"),
    THIS_MONTH("This month"),
    CUSTOM("Custom");

    companion object{
        fun getByValue(value: String?): DateRangeValue?{
            return try{
                when(value){
                    TODAY.value -> TODAY
                    YESTERDAY.value -> YESTERDAY
                    THIS_WEEK.value -> THIS_WEEK
                    THIS_MONTH.value -> THIS_MONTH
                    CUSTOM.value -> CUSTOM
                    else -> null
                }
            }catch (e: Exception){
                null
            }
        }
    }


}