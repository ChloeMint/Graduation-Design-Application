package com.example.greenplant.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.greenplant.Repository

class DeleteDongtaiViewModel : ViewModel() {
    private val dongtaiIdLiveData = MutableLiveData<Int>()
    val deleteDongtaiResponseLiveData = dongtaiIdLiveData.switchMap {
        Repository.deleteDongtai(it)
    }
    fun setDongtaiIdLiveData(dongtaiInt: Int){
        dongtaiIdLiveData.value = dongtaiInt
    }
}