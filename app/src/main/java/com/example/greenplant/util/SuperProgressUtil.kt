package com.example.greenplant.util

object SuperProgressUtil {
    fun killApp(){
        android.os.Process.killProcess(android.os.Process.myPid())
    }
}