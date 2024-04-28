package com.example.greenplant.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.greenplant.Repository
import okhttp3.MediaType
import okhttp3.RequestBody

class ChangeUsernameViewModel : ViewModel() {
    private val usernameLiveData = MutableLiveData<String>()
    val changeUsernameResponseLiveData = usernameLiveData.switchMap {
        val json = "{\"username\":\"${it}\"}" // 替换为你的JSON数据
        val mediaType = MediaType.parse("application/json; charset=utf-8")
        val requestBody = RequestBody.create(mediaType,json)
        Repository.changeUsername(requestBody)
    }
    fun setUsernameLiveData(username:String){
        usernameLiveData.value = username
    }
}