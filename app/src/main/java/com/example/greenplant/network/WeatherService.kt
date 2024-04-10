package com.example.greenplant.network

import com.example.greenplant.entities.NearByDayWeather
import com.example.greenplant.entities.ProvinceResponse
import com.example.greenplant.entities.WeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherService {
    @GET("https://api.kertennet.com/geography/locationInfo")
    fun getProvince(@Query("lat") lat:Double, @Query("lng") lng:Double):Call<ProvinceResponse>

    @GET("https://api.caiyunapp.com/v2.5/{token}/{lng},{lat}/realtime.json")
    fun getRealtime(@Path("token") token:String, @Path("lng") lng: Double, @Path("lat") lat: Double):Call<WeatherResponse>

    @GET("https://api.caiyunapp.com/v2.6/{token}/{lng},{lat}/daily")
    fun getNearByDay(@Path("token") token: String, @Path("lng") lng: Double, @Path("lat") lat: Double, @Query("dailysteps") dailysteps: Int):Call<NearByDayWeather>
}