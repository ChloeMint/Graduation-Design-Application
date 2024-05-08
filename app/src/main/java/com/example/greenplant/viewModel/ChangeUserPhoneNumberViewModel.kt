package com.example.greenplant.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.greenplant.Repository
import com.example.greenplant.entities.PhoneAndCode
import okhttp3.MediaType
import okhttp3.RequestBody

class ChangeUserPhoneNumberViewModel : ViewModel() {
    private val phoneAndCodeLiveData = MutableLiveData<PhoneAndCode>()
    val changeUserPhoneNumberLiveData = phoneAndCodeLiveData.switchMap {
        val json = "{\"phone\":\"${it.phone}\", \"code\":\"${it.code}\"}" // 替换为你的JSON数据
        val mediaType = MediaType.parse("application/json; charset=utf-8")
        val requestBody = RequestBody.create(mediaType,json)
        Repository.changeUserPhoneNUmber(requestBody)
    }
    fun setPhoneAndCode(phone:String,code:String){
        phoneAndCodeLiveData.value = PhoneAndCode(phone, code)
    }
}