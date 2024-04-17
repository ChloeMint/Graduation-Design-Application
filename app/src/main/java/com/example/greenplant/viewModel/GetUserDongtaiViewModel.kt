package com.example.greenplant.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.greenplant.Repository
import com.example.greenplant.entities.SelfDongtai

class GetUserDongtaiViewModel : ViewModel() {
    private val userIdLiveData = MutableLiveData<SelfDongtai>()
    val userDongtaiLiveData = userIdLiveData.switchMap {
        Repository.getUserDongtai(it.userId,it.page)
    }
    fun setUserId(userId:Int, page:Int = 1){
        userIdLiveData.value = SelfDongtai(userId,page)
    }
}