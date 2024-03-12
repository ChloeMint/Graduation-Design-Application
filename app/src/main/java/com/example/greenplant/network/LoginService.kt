package com.example.greenplant.network

import com.example.greenplant.entities.LoginResponse
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {
    @POST("/login")
    fun login(@Body requestBody : RequestBody):Call<LoginResponse>
}