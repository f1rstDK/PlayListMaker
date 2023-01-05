package com.example.playlistmakers_doing

import com.google.gson.annotations.SerializedName

data class Tracks(
    @SerializedName("trackTimeMillis") val trackTimeMillis: Int? = null,
    @SerializedName("artWorkUrl100") val artWorkUrl100: String? = null,
    @SerializedName("artistName") val artistName: String? = null,
    @SerializedName("trackName") val trackName: String? = null
)
