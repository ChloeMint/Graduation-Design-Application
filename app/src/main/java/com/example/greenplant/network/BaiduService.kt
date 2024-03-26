package com.example.greenplant.network

import com.example.greenplant.entities.BaiDuPlatformTokenResponse
import com.example.greenplant.entities.BaiduPlantRecognitionResponse
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface BaiduService {
    @GET("https://aip.baidubce.com/oauth/2.0/token?client_id=C530j43TypAgADv4x6chYrIG&client_secret=4PjSs4HYLW9CimEqdcibAEfj2MqlenNN&grant_type=client_credentials")
    fun getBaiduToken(): Call<BaiDuPlatformTokenResponse>

    @FormUrlEncoded
    @POST("https://aip.baidubce.com/rest/2.0/image-classify/v1/plant")
    fun getBaiduPlantRecognition(
        @Field("access_token") accessToken:String,
        @Field("image") image:String
    ):Call<BaiduPlantRecognitionResponse>

}