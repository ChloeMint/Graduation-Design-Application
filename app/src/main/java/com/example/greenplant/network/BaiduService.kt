package com.example.greenplant.network

import com.example.greenplant.entities.BaiDuPlatformTokenResponse
import retrofit2.Call
import retrofit2.http.GET

interface BaiduService {
    @GET("https://aip.baidubce.com/oauth/2.0/token?client_id=C530j43TypAgADv4x6chYrIG&client_secret=4PjSs4HYLW9CimEqdcibAEfj2MqlenNN&grant_type=client_credentials")
    fun getBaiduToken(): Call<BaiDuPlatformTokenResponse>
}