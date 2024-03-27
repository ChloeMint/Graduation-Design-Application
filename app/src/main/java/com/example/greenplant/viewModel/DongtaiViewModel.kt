package com.example.greenplant.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.greenplant.Repository

class DongtaiViewModel : ViewModel() {
    private val page = MutableLiveData<Int>()
    val dongtaiResponse = page.switchMap {
        Repository.getDongTai(it)
    }
    fun setPage(page:Int){
        this.page.value = page
    }
}