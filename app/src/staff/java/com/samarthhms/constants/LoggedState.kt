package com.samarthhms.constants

enum class LoggedState(val value: String) {
    LOGGED_IN("Logged_In"),
    LOGGED_OUT("Logged_Out");

    companion object{
        fun getByValue(value: String?): LoggedState{
            return when(value){
                LOGGED_IN.value -> LOGGED_IN
                else -> LOGGED_OUT
            }
        }
    }
}