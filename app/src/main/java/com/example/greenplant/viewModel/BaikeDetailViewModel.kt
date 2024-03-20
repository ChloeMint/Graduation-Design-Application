package com.example.greenplant.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.greenplant.Repository

class BaikeDetailViewModel : ViewModel() {
    private val plantId = MutableLiveData<Int>()
    val baikeDetailResponse = plantId.switchMap {
        Repository.getBaikeDetail(it)
    }
    fun setPlantId(plantId:Int){
        this.plantId.value = plantId
    }
}