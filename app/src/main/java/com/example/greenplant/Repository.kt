package com.example.greenplant

import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import okhttp3.RequestBody

object Repository {
    fun login(requestBody: RequestBody) = liveData(Dispatchers.IO) {
        val result = try {
            val loginResponse = GreenPlantNetwork.login(requestBody)
            if (loginResponse.code == 200){
                val token = loginResponse.data
                Result.success(token)
            }else{
                Result.failure(RuntimeException("${loginResponse.msg}"))
            }
        }catch (e : Exception){
            Result.failure(e)
        }
        emit(result)
    }
}