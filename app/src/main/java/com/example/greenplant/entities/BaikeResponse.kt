package com.example.greenplant.entities

import com.google.gson.annotations.SerializedName

data class BaikeResponse(
    val count:Int,
    val data:List<Baike>
):BasicResponse()

data class Baike(
    val id:Int,
    val area:String,
    @SerializedName("care_knowledge") val careKnowledge: String,
    val imageList:List<String>,
    val introduction:String,
    val legendStory:String,
    val plantCulture:String,
    @SerializedName("plant_english_name") val plantEnglishName: String,
    @SerializedName("plant_name") val plantName: String
    )

data class BaikeDetailResponse(
    val data:Baike
) : BasicResponse()