package com.example.greenplant

import org.junit.Test

import org.junit.Assert.*
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        val dateTimeString = "2024-04-11T00:00+08:00"
        val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME
        val zonedDateTime = ZonedDateTime.parse(dateTimeString, formatter)
        val localDate = zonedDateTime.toLocalDate()
        val simpleDateString = localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))

        println(simpleDateString) // 输出: 2024-04-11
    }
}