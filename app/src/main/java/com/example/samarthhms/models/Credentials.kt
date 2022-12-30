package com.example.samarthhms.models

import com.example.samarthhms.constants.Role

data class Credentials(
    val id: String,
    val role: Role,
    val username: String,
    val password: String
)
