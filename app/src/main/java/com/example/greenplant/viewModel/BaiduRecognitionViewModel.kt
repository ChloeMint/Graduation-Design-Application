package com.example.greenplant.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.greenplant.Repository
import com.example.greenplant.util.DefaultPreferencesUtil
import org.w3c.dom.Text

class BaiduRecognitionViewModel : ViewModel() {
    private val base64Image = MutableLiveData<String>()
    val baiduPlantRecognitionResponse = base64Image.switchMap {
        Repository.getBaiduRecognition(DefaultPreferencesUtil.getBaiduToken(),it)
    }
    fun setBase64Image(image:String){
        base64Image.value = image
    }
}