package com.example.greenplant.util

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import androidx.annotation.RequiresApi
import org.joda.time.DateTime
import org.joda.time.format.DateTimeParser
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*

object SuperDateUtil {
    const val ONE_MINUTE = 60 * 1000L
    const val ONE_HOUR =  60 * ONE_MINUTE
    const val ONE_DAY = 24 * ONE_HOUR
    const val DATETIMEPARTTEN = "yyyy-MM-dd HH:mm"
    fun getCurrentYear():Int{
        return Calendar.getInstance().get(Calendar.YEAR)
    }

    fun commonFormat(data:String):String{
        val dateTime = DateTime(data)
        return dateTimeFormat(dateTime)
    }

    private fun dateTimeFormat(dateTime: DateTime):String{
        // 计算现在和之前发布时间的时间差
        // 单位为毫秒
        val value = Date().time - dateTime.toDate().time
        if (value < 1L * ONE_MINUTE){
            val data = toSeconds(value)
            return String.format("%d秒前",if (data <=0) 1 else data)
        }else if (value < 60 * ONE_MINUTE){
            // 小于1小时，显示分钟
            val data = toMinutes(value)
            return String.format("%d分钟前",if (data <= 0) 1 else data)
        }else if (value < 24 * ONE_HOUR){
            val data = toHours(value)
            return String.format("%d小时前",if (data <= 0)1 else data)
        }else if (value < 30 * ONE_DAY){
            val data = toDays(value)
            return String.format("%d天前", if (data <= 0)1 else data)
        }
        return yyyyMMddHHmm(dateTime)
    }

    private fun toSeconds(value : Long): Long {
        return value/1000L
    }

    private fun toMinutes(value: Long):Long{
        return toSeconds(value)/60L
    }

    private fun toHours(value: Long):Long{
        return toMinutes(value)/60L
    }

    private fun toDays(value: Long):Long{
        return toHours(value)/24L
    }

    private fun yyyyMMddHHmm(dateTime: DateTime):String{
        return dateTime.toString(DATETIMEPARTTEN)
    }
}