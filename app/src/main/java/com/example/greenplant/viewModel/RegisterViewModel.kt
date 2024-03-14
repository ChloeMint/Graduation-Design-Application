package com.example.greenplant.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.greenplant.Repository
import com.example.greenplant.entities.User
import okhttp3.MediaType
import okhttp3.RequestBody

class RegisterViewModel : ViewModel() {
    private val userLiveData = MutableLiveData<User>()

    val registerResponseLiveData = userLiveData.switchMap {
        val json = "{\"phone\":\"${it.phone}\", \"password\":\"${it.password}\",\"code\":\"${it.code}\", \"username\":\"${it.username}\"}" // 替换为你的JSON数据
        val mediaType = MediaType.parse("application/json; charset=utf-8")
        val requestBody = RequestBody.create(mediaType,json)
        Repository.register(requestBody)
    }

    fun setUserLiveData(user: User){
        userLiveData.value = user
    }
}