package com.example.greenplant

import android.util.Log
import androidx.lifecycle.liveData
import com.example.greenplant.util.DefaultPreferencesUtil
import kotlinx.coroutines.Dispatchers
import okhttp3.MultipartBody
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

    fun getDongTai(page: Int) = fire(Dispatchers.IO){
        val dongtaiResponse = GreenPlantNetwork.getDongTai(page)
        if (dongtaiResponse.code != 500){
            Result.success(dongtaiResponse.data)
        }else{
            Result.failure(RuntimeException(dongtaiResponse.msg))
        }
    }

    fun likeAndCancel(dongtaiId:Int) = fire(Dispatchers.IO){
        val likeAndCancelResponse = GreenPlantNetwork.likeAndCancel(dongtaiId)
        if (likeAndCancelResponse.code != 500){
            Result.success(likeAndCancelResponse.msg)
        }else{
            Result.failure(RuntimeException(likeAndCancelResponse.msg))
        }
    }

    fun publishComment(dongtaiId: Int, requestBody: RequestBody) = fire(Dispatchers.IO){
        val publishCommentResponse = GreenPlantNetwork.publishComment(dongtaiId, requestBody)
        if (publishCommentResponse.code != 500){
            Result.success(publishCommentResponse)
        }else{
            Result.failure(RuntimeException(publishCommentResponse.msg))
        }
    }

    fun publishDongtaiImage(text:String, images:List<MultipartBody.Part>) = fire(Dispatchers.IO){
        Log.d("Repository", text)
        val  publishDongtaiImageResponse = GreenPlantNetwork.publishDongtaiImage(text, images)
        if (publishDongtaiImageResponse.code != 500){
            Result.success(publishDongtaiImageResponse.msg)
        }else{
            Result.failure(RuntimeException(publishDongtaiImageResponse.msg))
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
