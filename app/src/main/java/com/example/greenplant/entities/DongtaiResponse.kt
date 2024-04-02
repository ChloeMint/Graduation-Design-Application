package com.example.greenplant.entities


data class DongtaiResponse(
    val count:Int,
    val data:List<Dongtai>
):BasicResponse()

data class Dongtai(
    val article_text:String,
    val comments:List<Comment>,
    val id: Int,
    val imageList:List<String>,
    val like_num:Int,
    val publish_time:String,
    val user_id:Int,
    val user:User,
    val like:List<Like>
)

data class Comment(
    val article_id:Int,
    val comment_text:String,
    val comment_user_id:Int,
    val id: Int,
    val user:User
)

data class Like(
    val article_id:Int,
    val id:Int,
    val user_id: Int
)