package com.example.greenplant.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.greenplant.Repository
import okhttp3.MediaType
import okhttp3.RequestBody

class ChangeUserIntroduceViewModel: ViewModel() {
    private val introduceLiveData = MutableLiveData<String>()
    val userIntroduceLiveData = introduceLiveData.switchMap {
        val json = "{\"introduce\":\"${it}\"}" // 替换为你的JSON数据
        val mediaType = MediaType.parse("application/json; charset=utf-8")
        val requestBody = RequestBody.create(mediaType,json)
        Repository.changeUserIntroduce(requestBody)
    }
    fun setIntroduceLiveData(introduce:String) {
        introduceLiveData.value = introduce
    }
}