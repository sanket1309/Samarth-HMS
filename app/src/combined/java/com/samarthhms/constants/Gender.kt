package com.samarthhms.constants

enum class Gender (val value: String) {
    MALE("Male"),
    FEMALE("Female"),
    NONE("None");

    companion object{
        fun getByValue(value: String?): Gender{
            return when(value){
                MALE.value -> MALE
                FEMALE.value -> FEMALE
                else -> NONE
            }
        }
    }


}