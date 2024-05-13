package com.example.greenplant.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.greenplant.Repository

class CancelAccountViewModel : ViewModel() {
    private val flagLiveData = MutableLiveData<Boolean>()
    val cancelAccountLiveData = flagLiveData.switchMap {
        Repository.cancelAccount()
    }
    fun setFlag(flag:Boolean){
        flagLiveData.value = flag
    }
}