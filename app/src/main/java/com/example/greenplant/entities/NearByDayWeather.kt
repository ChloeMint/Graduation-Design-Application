package com.example.greenplant.entities

data class NearByDayWeather(
    val status:String,
    val result:DailyResult
)

data class DailyResult(
    val daily:Daily
)

data class Daily(
    val temperature:List<Temperature>,
    val skycon:List<SkyCon>,
    val life_index: LifeIndex
)

data class Temperature(
    val date:String,
    val max:Double,
    val min:Double
)

data class SkyCon(
    val date: String,
    val value:String
)

data class LifeIndex(
    val ultraviolet:List<Ultraviolet>,
    val carWashing:List<CarWashing>,
    val dressing:List<Dressing>,
    val coldRisk:List<ColdRisk>
)

data class Ultraviolet(
    val date:String,
    val desc:String
)

data class CarWashing(
    val date:String,
    val desc:String
)

data class Dressing(
    val date:String,
    val desc:String
)

data class ColdRisk(
    val date:String,
    val desc:String
)