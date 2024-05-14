package com.example.greenplant.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.greenplant.Repository
import com.example.greenplant.entities.Bug
import okhttp3.MediaType
import okhttp3.RequestBody

class SubmitBugViewModel : ViewModel() {
    private val bugLiveData = MutableLiveData<Bug>()
    val submitBugResponseLiveData = bugLiveData.switchMap {
        val json = "{\"subject\":\"${it.subject}\", \"content\":\"${it.content}\"}" // 替换为你的JSON数据
        val mediaType = MediaType.parse("application/json; charset=utf-8")
        val requestBody = RequestBody.create(mediaType,json)
        Repository.submitBug(requestBody)
    }
    fun setBug(subject:String, content:String){
        bugLiveData.value = Bug(subject, content)
    }
}