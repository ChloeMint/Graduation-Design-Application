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

    fun saveToken(token:String){
        defaultPreferences.edit{
            putString("token", token)
        }
    }

    fun getToken(): String {
        return defaultPreferences.getString("token", "")!!
    }

    fun saveBaiduToken(token: String){
        defaultPreferences.edit{
            putString("baiduToken", token)
        }
    }

    fun getBaiduToken(): String {
        return defaultPreferences.getString("baiduToken", "")!!
    }

    fun saveBaiduExpiresTime(time : Long){
        defaultPreferences.edit{
            putLong("baiduExpiresTime", time)
        }
    }

    fun getBaiduExpiresTime(): Long {
        return defaultPreferences.getLong("baiduExpiresTime", 0)!!
    }
}