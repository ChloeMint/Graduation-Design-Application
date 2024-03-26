package com.example.greenplant.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.greenplant.Repository

class BaiduTokenViewModel : ViewModel() {
    private val start = MutableLiveData<Int>()
    val baiDuPlatformTokenResponse = start.switchMap {
        Repository.getBaiduToken()
    }
    fun setStart(number:Int){
        start.value = number
    }
}