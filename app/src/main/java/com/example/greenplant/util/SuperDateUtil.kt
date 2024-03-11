package com.example.greenplant.util

import android.content.Context
import android.content.res.Configuration
import java.util.Calendar

object SuperDateUtil {
    fun getCurrentYear():Int{
        return Calendar.getInstance().get(Calendar.YEAR)
    }
}