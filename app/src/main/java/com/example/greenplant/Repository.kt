package com.example.greenplant

import android.util.Log
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import okhttp3.RequestBody
import kotlin.coroutines.CoroutineContext

object Repository {
    fun login(requestBody: RequestBody) = fire(Dispatchers.IO) {
        val loginResponse = GreenPlantNetwork.login(requestBody)
        if (loginResponse.code != 500){
            val token = loginResponse.data
            Result.success(token)
        }else{
            Result.failure(RuntimeException(loginResponse.msg))
        }
    }

    fun getUserInfo() = fire(Dispatchers.IO){
        val userInfoResponse = GreenPlantNetwork.getUserInfo()
        if (userInfoResponse.code != 500){
            Result.success(userInfoResponse.data)
        }else{
            Result.failure(java.lang.RuntimeException(userInfoResponse.msg))
        }
    }

    fun sendMessage(requestBody: RequestBody) = fire(Dispatchers.IO){
        val registerResponse = GreenPlantNetwork.sendMessage(requestBody)
        if (registerResponse.code != 500){
            Result.success(registerResponse.msg)
        }else{
            Result.failure(java.lang.RuntimeException(registerResponse.msg))
        }
    }

    fun forgetPassword(requestBody: RequestBody) = fire(Dispatchers.IO){
        val forgetPasswordResponse = GreenPlantNetwork.forgetPassword(requestBody)
        if (forgetPasswordResponse.code != 500){
            Result.success(forgetPasswordResponse.msg)
        }else{
            Result.failure(java.lang.RuntimeException(forgetPasswordResponse.msg))
        }
    }

    fun register(requestBody: RequestBody) = fire(Dispatchers.IO){
        val registerResponse = GreenPlantNetwork.register(requestBody)
        if (registerResponse.code != 500){
            Result.success(registerResponse.msg)
        }else{
            Result.failure(java.lang.RuntimeException(registerResponse.msg))
        }
    }


    private fun <T> fire(context: CoroutineContext, block: suspend () -> Result<T>) =
        liveData(context) {
            val result = try {
                block()
            } catch (e: Exception) {
                Result.failure<T>(e)
            }
            emit(result)
        }
}