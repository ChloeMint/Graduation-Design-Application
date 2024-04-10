package com.example.greenplant.entities

data class WeatherResponse(
    val status:String,
    val result:WeatherResult
)

data class WeatherResult(
    val realtime:Realtime
)

data class Realtime(
    val temperature: Double,
    val skycon: String,
    val air_quality:AirQuality,
)

data class AirQuality(
    val aqi:AQI
)
data class AQI(
    val chn:Int
)