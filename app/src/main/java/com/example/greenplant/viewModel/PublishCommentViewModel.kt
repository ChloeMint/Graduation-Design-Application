package com.example.greenplant.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.greenplant.Repository
import com.example.greenplant.entities.PublishComment
import okhttp3.MediaType
import okhttp3.RequestBody

class PublishCommentViewModel : ViewModel() {
    private val dongtaiIdLiveData = MutableLiveData<PublishComment>()
    val publishCommentLiveData = dongtaiIdLiveData.switchMap {
        val json = "{\"text\":\"${it.text}\"}" // 替换为你的JSON数据
        val mediaType = MediaType.parse("application/json; charset=utf-8")
        val requestBody = RequestBody.create(mediaType,json)
        Repository.publishComment(it.dongtaiId, requestBody)
    }
    fun setDongtaiIdLiveData(publishComment: PublishComment){
        dongtaiIdLiveData.value = publishComment
    }
}