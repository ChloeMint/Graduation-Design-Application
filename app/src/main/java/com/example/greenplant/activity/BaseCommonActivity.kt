package com.example.greenplant.activity

import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.util.Base64
import androidx.annotation.RequiresApi
import com.example.greenplant.util.ActivityCollector
import java.io.ByteArrayOutputStream
import java.nio.file.Files
import java.nio.file.Paths


open class BaseCommonActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityCollector.addActivity(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        ActivityCollector.removeActivity(this)
    }

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