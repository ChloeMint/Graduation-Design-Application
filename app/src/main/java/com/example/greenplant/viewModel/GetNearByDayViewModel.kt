package com.example.greenplant.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.greenplant.Repository
import com.example.greenplant.ServiceCreator
import com.example.greenplant.entities.Position

class GetNearByDayViewModel : ViewModel() {
    private val positionLiveData = MutableLiveData<Position>()
    val nearByDayLiveData = positionLiveData.switchMap {
        Repository.getNearByDay(ServiceCreator.Weather_API_TOKEN, it.lng, it.lat, 4)
    }
    fun setPositionLiveData(position: Position){
        positionLiveData.value = position
    }
}