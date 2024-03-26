package com.example.greenplant

import android.util.Log
import androidx.lifecycle.liveData
import com.example.greenplant.util.DefaultPreferencesUtil
import kotlinx.coroutines.Dispatchers
import okhttp3.RequestBody
import org.w3c.dom.Text
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

    fun getBaike(page:Int) = fire(Dispatchers.IO){
        val baikeResponse = GreenPlantNetwork.getBaike(page)
        if (baikeResponse.code != 500){
            Result.success(baikeResponse.data)
        }else{
            Result.failure(java.lang.RuntimeException(baikeResponse.msg))
        }
    }

    fun getBaikeDetail(plantId:Int) = fire(Dispatchers.IO){
        val baikeDetailResponse = GreenPlantNetwork.getBaikeDetail(plantId)
        if (baikeDetailResponse.code != 500){
            Result.success(baikeDetailResponse.data)
        }else{
            Result.failure(java.lang.RuntimeException(baikeDetailResponse.msg))
        }
    }

    fun getBaikeSearch(plantName:String) = fire(Dispatchers.IO){
        val baikeSearchResponse = GreenPlantNetwork.getBaikeSearch(plantName)
        if (baikeSearchResponse.code != 500){
            Result.success(baikeSearchResponse.data)
        }else{
            Result.failure(java.lang.RuntimeException(baikeSearchResponse.msg))
        }
    }

    fun getBaiduToken()= fire(Dispatchers.IO){
        val baiDuPlatformTokenResponse = GreenPlantNetwork.getBaiduToken()
        if (baiDuPlatformTokenResponse.access_token != null){
            Result.success(baiDuPlatformTokenResponse)
        }else{
            Result.failure(java.lang.RuntimeException("token get failed"))
        }
    }

    fun getBaiduRecognition(accessToken:String, image:String) = fire(Dispatchers.IO){
        val baiduPlantRecognitionResponse = GreenPlantNetwork.getBaiduRecognition(accessToken, image)
        Log.d("Repository", "$baiduPlantRecognitionResponse")
        Result.success(baiduPlantRecognitionResponse.result)
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
