package com.example.playlistmakers_doing

import android.content.SharedPreferences
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
