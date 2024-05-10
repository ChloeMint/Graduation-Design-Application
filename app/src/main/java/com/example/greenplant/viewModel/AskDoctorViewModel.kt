package com.example.greenplant.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.greenplant.Repository

class AskDoctorViewModel : ViewModel() {
    private val questionLiveData = MutableLiveData<String>()
    val askQuestionResponseLiveData  = questionLiveData.switchMap {
        Repository.askDoctor(it)
    }
    fun setQuestion(question:String){
        questionLiveData.value = question
    }
}