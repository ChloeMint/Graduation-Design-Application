package com.example.greenplant.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.greenplant.Repository
import com.example.greenplant.entities.DongtaiWithVideo
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class PublishDongtaiWithVideoViewModel : ViewModel() {
    private val dongtaiLiveData = MutableLiveData<DongtaiWithVideo>()
    val publishDongtaiWithVideoLiveData = dongtaiLiveData.switchMap {
        val textRequestBody: RequestBody = RequestBody.create(
            MediaType.parse("text/plain"),
            it.text
        )

        val file = File(it.video.path)

        val requestFile = RequestBody.create(
            MediaType.parse("video/mp4"),
            file
        )
        val part = MultipartBody.Part.createFormData("video", file.name, requestFile)

        Repository.publishDongtaiWithVideo(textRequestBody,part)
    }
    fun setPublishDongtaiWithVideoLiveData(dongtai:DongtaiWithVideo){
        dongtaiLiveData.value = dongtai
    }
}