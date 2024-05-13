package com.example.greenplant.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.greenplant.Repository

class GetUserPersonalInfoViewModel : ViewModel() {
    private val userIdLiveData = MutableLiveData<Int>()
    val getUserPersonalInfoLiveData = userIdLiveData.switchMap {
        Repository.getUserPersonalInfo(it)
    }
    fun setUserId(userId: Int){
        userIdLiveData.value = userId
    }
}