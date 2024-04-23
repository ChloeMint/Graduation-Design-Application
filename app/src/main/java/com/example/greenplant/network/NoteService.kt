package com.example.greenplant.network

import com.example.greenplant.entities.BaseResponseDataClass
import com.example.greenplant.entities.GetNoteDetailResponse
import com.example.greenplant.entities.GetNoteResponse
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface NoteService {
    @GET("/note")
    fun getNotes(): Call<GetNoteResponse>

    @DELETE("/note/delete/{noteId}")
    fun deleteNote(@Path("noteId") noteId: Int): Call<BaseResponseDataClass>

    @GET("/note/{noteId}")
    fun getNoteDetail(@Path("noteId") noteId: Int) : Call<GetNoteDetailResponse>

    @PUT("/note/editing/{noteId}")
    fun editNote(@Path("noteId") noteId: Int, @Body requestBody: RequestBody) : Call<BaseResponseDataClass>
}