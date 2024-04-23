package com.example.greenplant.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.greenplant.Repository
import com.example.greenplant.entities.CreateNote
import okhttp3.MediaType
import okhttp3.RequestBody

class CreateNoteViewModel : ViewModel(){
    private val noteLiveData = MutableLiveData<CreateNote>()
    val createNoteLiveData = noteLiveData.switchMap {
        val json = "{\"title\":\"${it.title}\", \"content\":\"${it.content}\"}" // 替换为你的JSON数据
        val mediaType = MediaType.parse("application/json; charset=utf-8")
        val requestBody = RequestBody.create(mediaType, json)
        Repository.createNote(requestBody)
    }
    fun setNoteLiveData(title: String, content:String){
        noteLiveData.value = CreateNote(title, content)
    }
}