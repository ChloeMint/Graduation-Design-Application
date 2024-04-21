package com.example.greenplant.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.greenplant.Repository

class DeleteNoteViewModel : ViewModel() {
    private val noteId = MutableLiveData<Int>()
    val deleteResponseLiveData = noteId.switchMap {
        Repository.deleteNote(it)
    }
    fun setNoteId(noteId: Int){
        this.noteId.value = noteId
    }
}