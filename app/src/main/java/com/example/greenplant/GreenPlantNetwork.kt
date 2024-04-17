package com.example.greenplant

import com.example.greenplant.network.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

object GreenPlantNetwork {
    private val userService = ServiceCreator.create<UserService>()
    private val plantService = ServiceCreator.create<PlantService>()
    private val baiduService = ServiceCreator.create<BaiduService>()
    private val dongtaiService = ServiceCreator.create<DongtaiService>()
    private val weatherService = ServiceCreator.create<WeatherService>()

    suspend fun login(requestBody: RequestBody) = userService.login(requestBody).await()

    suspend fun getUserInfo() = userService.getUserInfo().await()

    suspend fun sendMessage(requestBody: RequestBody) = userService.sendMessage(requestBody).await()

    suspend fun forgetPassword(requestBody: RequestBody) = userService.forgetPassword(requestBody).await()

    suspend fun register(requestBody: RequestBody) = userService.register(requestBody).await()

    suspend fun getBaike(page:Int) = plantService.getBaike(page).await()

    suspend fun getBaikeDetail(plantId:Int) = plantService.getBaikeDetail(plantId).await()

    suspend fun getBaikeSearch(plantName: String) = plantService.getBaikeSearch(plantName).await()

    suspend fun getBaiduToken() = baiduService.getBaiduToken().await()

    suspend fun getBaiduRecognition(accessToken:String, image:String) = baiduService.getBaiduPlantRecognition(accessToken, image).await()

    suspend fun getDongTai(page: Int) = dongtaiService.getDongtais(page).await()

    suspend fun likeAndCancel(dongtaiId:Int) = dongtaiService.likeAndCancel(dongtaiId).await()

    suspend fun publishComment(dongtaiId: Int, requestBody: RequestBody) = dongtaiService.publishComment(dongtaiId, requestBody).await()

    suspend fun publishDongtaiImage(text:RequestBody, images:List<MultipartBody.Part>) = dongtaiService.publishDongtaiImage(text,images).await()

    suspend fun publishDongtaiWithVideo(text: RequestBody, video: MultipartBody.Part) = dongtaiService.publishDongtaiWithVideo(text, video).await()

    suspend fun publishDongtaiText(text: RequestBody) = dongtaiService.publishDongtaiText(text).await()

    suspend fun deleteDongtai(dongtaiId: Int) = dongtaiService.deleteDongtai(dongtaiId).await()

    suspend fun getProvince(lat:Double,lng:Double) = weatherService.getProvince(lat, lng).await()

    suspend fun getRealtime(token:String, lng: Double, lat: Double) = weatherService.getRealtime(token, lng, lat).await()

    suspend fun getNearByDay(token:String, lng: Double, lat: Double, dailysteps:Int) = weatherService.getNearByDay(token, lng, lat, dailysteps).await()

    suspend fun getUserDongtai(userId:Int, page: Int) = dongtaiService.getUserDongtai(userId, page).await()
    private suspend fun <T> Call<T>.await(): T {
        return suspendCoroutine {
            enqueue(object : Callback<T>{
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()
                    if (body != null) it.resume(body)
                    else it.resumeWithException(
                        RuntimeException("response body is null"))
                }
                override fun onFailure(call: Call<T>, t: Throwable) {
                    it.resumeWithException(t)
                }

            })
        }
    }
}