package com.example.greenplant.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.greenplant.Repository
import com.example.greenplant.entities.EditNote
import okhttp3.MediaType
import okhttp3.RequestBody

class EditNoteViewModel : ViewModel() {
    private val noteLiveData = MutableLiveData<EditNote>()
    val editResponseLiveData = noteLiveData.switchMap {
        val json = "{\"title\":\"${it.title}\", \"content\":\"${it.content}\"}" // 替换为你的JSON数据
        val mediaType = MediaType.parse("application/json; charset=utf-8")
        val requestBody = RequestBody.create(mediaType, json)

        Repository.editNote(it.id, requestBody)
    }
    fun setNoteLiveData(noteId:Int, title:String, content:String){
        noteLiveData.value = EditNote(noteId, title, content)
    }
}