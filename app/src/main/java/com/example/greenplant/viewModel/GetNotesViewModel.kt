package com.example.greenplant.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.greenplant.Repository

class GetNotesViewModel : ViewModel() {
    private val flag = MutableLiveData<Int>()
    val noteResponse = flag.switchMap {
        Repository.getNotes()
    }
    fun setFlag(){
        flag.value = 1
    }
}