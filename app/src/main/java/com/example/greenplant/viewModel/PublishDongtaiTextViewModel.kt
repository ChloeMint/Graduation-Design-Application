package com.example.greenplant.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.greenplant.Repository
import okhttp3.MediaType
import okhttp3.RequestBody

class PublishDongtaiTextViewModel : ViewModel() {
    private val dongtaiLiveData = MutableLiveData<String>()
    val publishDongtaiTextLiveData = dongtaiLiveData.switchMap {
        val textRequestBody: RequestBody = RequestBody.create(
            MediaType.parse("text/plain"),
            it
        )
        Repository.publishDongtaiText(textRequestBody)
    }
    fun setDongtaiLiveData(text:String){
        dongtaiLiveData.value = text
    }
}