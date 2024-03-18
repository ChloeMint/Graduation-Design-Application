package com.example.greenplant.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.greenplant.Repository

class GetBaikeViewModel : ViewModel() {
    private val page = MutableLiveData<Int>()
    val baikeResponseLiveData = page.switchMap {
        Repository.getBaike(it)
    }
    fun setPage(page:Int){
        this.page.value = page
    }
}