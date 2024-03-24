package com.example.greenplant.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.greenplant.Repository

class BaikeSearchViewModel : ViewModel() {
    private val plantNameLiveData = MutableLiveData<String>()
    val plantSearchResponseLiveData = plantNameLiveData.switchMap {
        Repository.getBaikeSearch(it)
    }
    fun setPlantName(plantName:String){
        plantNameLiveData.value = plantName
    }
}