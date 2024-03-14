package com.example.greenplant.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.greenplant.Repository
import com.example.greenplant.entities.User
import okhttp3.MediaType
import okhttp3.RequestBody

class ForgetPasswordViewModel : ViewModel() {
    private val forgetPasswordLiveData = MutableLiveData<User>()

    val forgetPasswordResLiveData = forgetPasswordLiveData.switchMap {
        val json = "{\"phone\":\"${it.phone}\", \"code\":\"${it.code}\", \"password\":\"${it.password}\"}" // 替换为你的JSON数据
        val mediaType = MediaType.parse("application/json; charset=utf-8")
        val requestBody = RequestBody.create(mediaType,json)
        Repository.forgetPassword(requestBody)
    }

    fun setForgetPasswordLiveData(user: User){
        forgetPasswordLiveData.value = user
    }
}