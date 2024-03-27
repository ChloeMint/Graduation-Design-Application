package com.example.greenplant.network

import com.example.greenplant.entities.DongtaiResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface DongtaiService {
    @GET("/dongtai")
    fun getDongtais(@Query("page") page:Int): Call<DongtaiResponse>
}