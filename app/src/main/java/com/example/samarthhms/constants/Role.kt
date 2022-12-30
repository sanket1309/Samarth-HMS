package com.example.samarthhms.constants

enum class Role(val value: String) {
    ADMIN("Admin"),
    STAFF("Staff"),
    NONE("None");

    companion object{
        fun getByValue(value: String?): Role{
            return when(value){
                ADMIN.value -> ADMIN
                STAFF.value -> STAFF
                else -> NONE
            }
        }
    }
}