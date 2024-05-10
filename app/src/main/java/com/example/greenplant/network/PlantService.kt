package com.example.greenplant.network

import com.example.greenplant.entities.BaikeDetailResponse
import com.example.greenplant.entities.BaikeResponse
import com.example.greenplant.entities.ChatResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PlantService {
    @GET("/baike")
    fun getBaike(@Query("page") page:Int): Call<BaikeResponse>

    @GET("/baike/{plantId}")
    fun getBaikeDetail(@Path("plantId") plantId:Int):Call<BaikeDetailResponse>

    @GET("/baike/{plantName}")
    fun getBaikeSearch(@Path("plantName") plantName:String):Call<BaikeResponse>

    @GET("/docker")
    fun askDoctor(@Query("question") question:String):Call<ChatResponse>
}