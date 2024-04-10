package com.example.greenplant.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.greenplant.Repository
import com.example.greenplant.entities.Position

class ProvinceViewModel : ViewModel(){
    private val positionLiveData = MutableLiveData<Position>()
    val getProvinceResponseLiveData = positionLiveData.switchMap {
        Repository.getProvince(it.lat,it.lng)
    }
    fun setPositionLiveData(position: Position){
        positionLiveData.value = position
    }
}