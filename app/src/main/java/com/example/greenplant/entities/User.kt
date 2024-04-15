package com.example.greenplant.entities

class User (
    // 前端发送的信息
    val username:String = "",
    val phone:String = "",
    val password:String = "",
    val code:String = "",

    // 后端返回
    val avatar:String = "",
    val id : Int = 0,
    val introduction:String=""
)