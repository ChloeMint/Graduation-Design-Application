package com.example.greenplant.network

import com.example.greenplant.entities.BaikeResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PlantService {
    @GET("/baike")
    fun getBaike(@Query("page") page:Int): Call<BaikeResponse>
}