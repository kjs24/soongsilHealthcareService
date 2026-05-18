package com.ssu.soongsilhealthcare.core.util

object DateUtil {
    fun today(): String {
        return java.time.LocalDate.now().toString()
    }
}
