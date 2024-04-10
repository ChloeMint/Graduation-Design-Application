package com.example.greenplant.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.greenplant.Repository
import com.example.greenplant.ServiceCreator
import com.example.greenplant.entities.Position

class GetRealtimeViewModel : ViewModel() {
    private val positionLiveData = MutableLiveData<Position>()
    val realtimeLiveData = positionLiveData.switchMap {
        Repository.getRealtime(ServiceCreator.Weather_API_TOKEN,it.lng,it.lat)
    }
    fun setPositionLiveData(position: Position){
        positionLiveData.value = position
    }
}