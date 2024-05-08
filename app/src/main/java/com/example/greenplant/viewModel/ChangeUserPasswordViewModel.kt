package com.example.greenplant.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.greenplant.Repository
import okhttp3.MediaType
import okhttp3.RequestBody

class ChangeUserPasswordViewModel : ViewModel() {
    private val passwordLiveData = MutableLiveData<String>()
    val changeUserPasswordLiveData = passwordLiveData.switchMap {
        val json = "{\"password\":\"${it}\"}" // 替换为你的JSON数据
        val mediaType = MediaType.parse("application/json; charset=utf-8")
        val requestBody = RequestBody.create(mediaType,json)
        Repository.changeUserPassword(requestBody)
    }
    fun setPasswordLiveData(password:String){
        passwordLiveData.value = password
    }
}