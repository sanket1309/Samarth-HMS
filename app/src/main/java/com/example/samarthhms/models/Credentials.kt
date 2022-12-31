package com.example.samarthhms.models

import com.example.samarthhms.constants.Role

data class Credentials(
    val id: String = "def",
    val role: Role = Role.NONE,
    val username: String = "def",
    val password: String = "def"
)