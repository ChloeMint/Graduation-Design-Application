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
    val life_index:LifeIndex
)

data class AirQuality(
    val aqi:AQI
)
data class AQI(
    val chn:Int
)

data class LifeIndex(
    val ultraviolet:Ultraviolet,
    val comfort:Comfort
)

data class Ultraviolet(
    val desc:String
)

data class Comfort(
    val desc:String
)