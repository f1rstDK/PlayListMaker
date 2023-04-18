package com.example.playlistmakers_doing.data.shared

import android.content.SharedPreferences
import com.example.playlistmakers_doing.presentation.other.Constants
import com.example.playlistmakers_doing.domain.Track
import com.google.gson.Gson

class TrackSharedStore(private val sharedPreferences: SharedPreferences) {
    fun saveToSharedPrefs(track: Track) {
        sharedPreferences
            .edit()
            .putString(Constants.TRACK_DATA, Gson().toJson(track))
            .apply()
    }

    fun loadFromSharedPrefs(): Track? {
        val trackJson = sharedPreferences.getString(Constants.TRACK_DATA, "") ?: ""
        return Gson().fromJson(trackJson, Track::class.java)
    }

}
