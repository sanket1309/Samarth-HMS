package com.example.samarthhms

import com.samarthhms.utils.DateTimeUtils
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        fun getLocalDateTime(date: String?, time: String?): LocalDateTime? {
            if(date == null || time == null) return null
            return LocalDateTime.parse(date+" "+time.lowercase(), DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm a"))
        }
        try{
            System.out.println("TIME : "+DateTimeUtils.getLocalDateTime("01/01/2023","04:23 pm"))
        }catch (e : Exception){
            System.out.println("Excp : "+e)
        }
        assertEquals(4, 2 + 2)
    }
}