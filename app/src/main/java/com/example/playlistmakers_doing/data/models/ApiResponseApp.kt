package com.example.playlistmakers_doing.data.models

import com.google.gson.annotations.SerializedName

data class ApiResponseApp(
    @SerializedName("resultCount") val resultCount: Int,
    @SerializedName("results")
    val results: List<Tracks>
)

