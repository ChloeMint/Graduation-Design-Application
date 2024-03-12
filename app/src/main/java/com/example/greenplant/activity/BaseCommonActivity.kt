package com.example.greenplant.activity

import android.content.Intent

open class BaseCommonActivity : BaseActivity() {
    fun startActivityAfterFinishIt(clazz: Class<*>){
        val intent = Intent(this, clazz)
        finish()
        startActivity(intent)
    }

    fun startTheActivity(clazz: Class<*>){
        val intent = Intent(this,clazz)
        startActivity(intent)
    }
}