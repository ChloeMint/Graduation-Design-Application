package com.example.greenplant.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.greenplant.Repository

class LikeAndCancelViewModel : ViewModel() {
    private val dongtaiIdLiveData = MutableLiveData<Int>()
    val likeAndCancelLiveData = dongtaiIdLiveData.switchMap {
        Repository.likeAndCancel(it)
    }
    fun setDongtaiLiveData(dongtaiId:Int){
        dongtaiIdLiveData.value = dongtaiId
    }
}