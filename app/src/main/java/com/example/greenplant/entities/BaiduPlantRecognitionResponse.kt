package com.example.greenplant.entities

data class BaiduPlantRecognitionResponse(
    val result:List<PlantRecognition>
)

data class PlantRecognition(
    val score: Double,
    val name: String
)