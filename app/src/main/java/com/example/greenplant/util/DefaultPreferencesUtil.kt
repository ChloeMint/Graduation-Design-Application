package com.example.greenplant.util

import android.content.Context
import androidx.core.content.edit
import com.example.greenplant.GreenPlantApplication

object DefaultPreferencesUtil {
    private val defaultPreferences = GreenPlantApplication.context.getSharedPreferences("preferLike", Context.MODE_PRIVATE)

    fun serviceAgree(){
        defaultPreferences.edit {
            putBoolean("serviceAgree", true)
        }
    }

    fun isServiceAgree():Boolean{
        return defaultPreferences.getBoolean("serviceAgree", false)
    }
}