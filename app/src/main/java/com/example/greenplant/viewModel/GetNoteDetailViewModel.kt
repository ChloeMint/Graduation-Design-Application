package com.example.greenplant.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.greenplant.Repository

class GetNoteDetailViewModel : ViewModel() {
    private val noteIdLiveData = MutableLiveData<Int>()
    val noteDetailResponseLiveData = noteIdLiveData.switchMap {
        Repository.getNoteDetail(it)
    }
    fun setNoteId(noteId:Int){
        noteIdLiveData.value = noteId
    }
}