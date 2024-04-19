package com.example.greenplant.entities

data class GetNoteResponse(
    val count: Int,
    val data: List<Note>
) : BasicResponse()

data class Note(
    val content:String,
    val id:Int,
    val title:String,
    val user_id:Int
)