package com.example.greenplant.util

import android.content.Context
import android.content.res.Configuration

object SuperUiUtil {
    fun isDark(context: Context):Boolean{
        return context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
    }
}