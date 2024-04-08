package com.example.greenplant.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.greenplant.Repository
import com.example.greenplant.entities.DongtaiWithImage
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class PublishDongtaiImageViewModel : ViewModel() {
    private val dongtaiLiveData = MutableLiveData<DongtaiWithImage>()
    val publishDongtaiImageLiveData = dongtaiLiveData.switchMap {

        val images = mutableListOf<MultipartBody.Part>()
        val imageFiles = mutableListOf<File>() // 例如：listOf(File("path/to/image1.jpg"), File("path/to/image2.jpg"))

        for (i in it.images){
            imageFiles.add(File(i.path))
        }

        for (file in imageFiles) {
            val requestFile = RequestBody.create(
                MediaType.parse("image/jpeg"),
                file
            )
            val part = MultipartBody.Part.createFormData("files", file.name, requestFile)
            images.add(part)
        }

        val textRequestBody: RequestBody = RequestBody.create(
            MediaType.parse("text/plain"),
            it.text
        )


        Repository.publishDongtaiImage(textRequestBody,images)
    }
    fun setDongtaiImageLiveData(dongtai:DongtaiWithImage){
        dongtaiLiveData.value = dongtai
    }
}