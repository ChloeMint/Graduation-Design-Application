package com.example.greenplant.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.greenplant.Repository
import com.example.greenplant.entities.User

class UserInfoViewModel : ViewModel() {
    private val isGettingUserInfo = MutableLiveData<Boolean>()
    val userInfoLiveData = isGettingUserInfo.switchMap {
        Repository.getUserInfo()
    }
    fun setIsGettingUserInfo(){
        isGettingUserInfo.value = true
    }
}