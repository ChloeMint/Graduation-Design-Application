package com.example.greenplant.entities

data class BaiDuPlatformTokenResponse(
    val access_token:String,
    val expires_in:Long,
    val error_description:String,
    val error:String
)