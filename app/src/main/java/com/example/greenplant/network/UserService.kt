package com.example.greenplant.network

import com.example.greenplant.entities.BaseResponseDataClass
import com.example.greenplant.entities.LoginResponse
import com.example.greenplant.entities.MessageResponse
import com.example.greenplant.entities.UserInfoResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface UserService {
    @POST("/login")
    fun login(@Body requestBody : RequestBody):Call<LoginResponse>

    @GET("/user/getPersonalInfo")
    fun getUserInfo(): Call<UserInfoResponse>

    @POST("/sendMessage")
    fun sendMessage(@Body requestBody : RequestBody): Call<MessageResponse>

    @POST("/forgetPassword")
    fun forgetPassword(@Body requestBody: RequestBody) : Call<BaseResponseDataClass>

    @POST("/register")
    fun register(@Body requestBody: RequestBody) : Call<BaseResponseDataClass>

    @Multipart
    @PUT("/user/changeAvatar")
    fun changeUserAvatar(@Part image: MultipartBody.Part) : Call<BaseResponseDataClass>

    @PUT("/user/changeUsername")
    fun changeUsername(@Body requestBody: RequestBody): Call<BaseResponseDataClass>

    @PUT("/user/changeIntroduce")
    fun changeUserIntroduce(@Body requestBody: RequestBody) : Call<BaseResponseDataClass>

    @PUT("/user/changePhoneNumber")
    fun changeUserPhoneNumber(@Body requestBody: RequestBody) : Call<BaseResponseDataClass>
}