package com.example.greenplant.network

import com.example.greenplant.entities.BaseResponseDataClass
import com.example.greenplant.entities.DongtaiResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface DongtaiService {
    @GET("/dongtai")
    fun getDongtais(@Query("page") page:Int): Call<DongtaiResponse>

    @GET("/dongtai/{userId}")
    fun getUserDongtai(@Path("userId") userId:Int, @Query("page") page: Int):Call<DongtaiResponse>

    @GET("/dongtai/like/{dongtaiId}")
    fun likeAndCancel(@Path("dongtaiId") dongtaiId:Int): Call<BaseResponseDataClass>

    @POST("/comment/publish/{dongtaiId}")
    fun publishComment(@Path("dongtaiId") dongtaiId: Int, @Body requestBody:RequestBody): Call<BaseResponseDataClass>

    @Multipart
    @POST("/dongtai/publish")
    fun publishDongtaiImage(
        @Part("text") text: RequestBody,
        @Part images: List<MultipartBody.Part>
    ): Call<BaseResponseDataClass>

    @Multipart
    @POST("/dongtai/publish")
    fun publishDongtaiWithVideo(@Part("text") text: RequestBody, @Part video: MultipartBody.Part): Call<BaseResponseDataClass>

    @Multipart
    @POST("/dongtai/publish")
    fun publishDongtaiText(@Part("text") text: RequestBody): Call<BaseResponseDataClass>

    @DELETE("/dongtai/delete/{dongtaiId}")
    fun deleteDongtai(@Path("dongtaiId") dongtaiId: Int): Call<BaseResponseDataClass>

    @Multipart
    @PUT("/user/changeBackground")
    fun changeUserBackground(@Part file: MultipartBody.Part): Call<BaseResponseDataClass>
}