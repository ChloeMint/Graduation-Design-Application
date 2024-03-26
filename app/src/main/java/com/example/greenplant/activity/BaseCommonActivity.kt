package com.example.greenplant.activity

import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.util.Base64
import androidx.annotation.RequiresApi
import java.io.ByteArrayOutputStream
import java.nio.file.Files
import java.nio.file.Paths


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

//    fun bitmap2Byte(bitmap: Bitmap?): ByteArray? {
//        if (null == bitmap) throw NullPointerException()
//        // if (null == bitmap) return null;
//        val outputStream = ByteArrayOutputStream()
//        //把bitmap100%高质量压缩 到 output对象里
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
//        return outputStream.toByteArray()
//    }

//    fun byte2Base64(imageByte: ByteArray?): String? {
//        return if (null == imageByte) null else Base64.encodeToString(imageByte, Base64.DEFAULT)
//    }

    fun bitmapToBase64(bitmap: Bitmap): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.NO_WRAP)
    }
}