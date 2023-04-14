package com.example.playlistmakers_doing.data.models

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiMain {
    private const val BASEURL = "https://itunes.apple.com/"
    private val logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    private val httpClient = OkHttpClient.Builder().addInterceptor(logging)
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASEURL)
        .client(httpClient.build())
        .build()
    val apiaService: ItunesApi = retrofit.create(ItunesApi::class.java)
}