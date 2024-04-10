package com.example.greenplant.entities

data class ProvinceResponse (
    val code:Int,
    val message:String,
    val data:Address
)

data class Address(
    val city:String
)