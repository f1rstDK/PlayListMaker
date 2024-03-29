package com.example.playlistmakers_doing

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ItunesApi {
    @GET("/search?entity=song")
    fun search(@Query("term") text: String,
               @Query("entity") type: String = "song"): Call<ApiResponseApp>
}