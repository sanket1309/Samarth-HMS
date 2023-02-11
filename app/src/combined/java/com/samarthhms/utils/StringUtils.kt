package com.samarthhms.utils

class StringUtils {
    companion object{
        fun formatName(name: String): String {
            if (name.isBlank()) return name
            return name.lowercase().replaceFirstChar { it.uppercase() }
        }
    }
}