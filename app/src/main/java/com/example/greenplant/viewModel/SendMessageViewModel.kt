package com.example.greenplant.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.greenplant.Repository
import okhttp3.MediaType
import okhttp3.RequestBody

class SendMessageViewModel : ViewModel() {
    private val phoneLiveData = MutableLiveData<String>()

    val sendMessageResponseLiveData = phoneLiveData.switchMap {
        val json = "{\"phone\":\"${it}\"}" // 替换为你的JSON数据
        val mediaType = MediaType.parse("application/json; charset=utf-8")
        val requestBody = RequestBody.create(mediaType,json)
        Repository.sendMessage(requestBody)
    }

    fun setPhoneLiveData(phone:String){
        phoneLiveData.value = phone
    }
}