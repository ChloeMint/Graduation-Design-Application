package com.example.greenplant.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.greenplant.Repository
import com.luck.picture.lib.entity.LocalMedia
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class ChangeUserAvatarViewModel : ViewModel() {
    private val imageLocalMedia = MutableLiveData<LocalMedia>()
    val changeUserAvatarLiveData = imageLocalMedia.switchMap {
        val file = File(it.path)
        val requestBody = RequestBody.create(
            MediaType.parse("image/jpeg"),
            file
        )
        val part = MultipartBody.Part.createFormData("file", file.name, requestBody)
        Repository.changeUserAvatar(part)
    }
    fun setImageLocalMedia(localMedia: LocalMedia){
        imageLocalMedia.value = localMedia
    }
}