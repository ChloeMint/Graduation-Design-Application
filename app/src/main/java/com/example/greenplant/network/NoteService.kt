package com.example.greenplant.network

import com.example.greenplant.entities.BaseResponseDataClass
import com.example.greenplant.entities.GetNoteResponse
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path

interface NoteService {
    @GET("/note")
    fun getNotes(): Call<GetNoteResponse>

    @DELETE("/note/delete/{noteId}")
    fun deleteNote(@Path("noteId") noteId: Int): Call<BaseResponseDataClass>
}