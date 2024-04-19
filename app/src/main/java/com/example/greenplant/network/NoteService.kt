package com.example.greenplant.network

import com.example.greenplant.entities.GetNoteResponse
import retrofit2.Call
import retrofit2.http.GET

interface NoteService {
    @GET("/note")
    fun getNotes(): Call<GetNoteResponse>
}