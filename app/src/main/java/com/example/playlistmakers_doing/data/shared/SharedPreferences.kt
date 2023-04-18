package com.example.playlistmakers_doing.data.shared

import android.content.SharedPreferences
import com.example.playlistmakers_doing.presentation.other.Constants.COUNT_OF_TRACKS
import com.example.playlistmakers_doing.presentation.other.Constants.TRACKS_HISTORY_KEY
import com.example.playlistmakers_doing.domain.Track
import com.google.gson.Gson

class SharedPreferences(
    private val sharedPref: SharedPreferences
) {
    private var listOfTrack = mutableListOf<Track>()

    fun addToList(track: Track) {
        if(listOfTrack.contains(track)) {
            listOfTrack.remove(track)
        }
        listOfTrack.add(track)
        cropList()
        val json = Gson().toJson(listOfTrack)
        sharedPref.edit()
            .putString(TRACKS_HISTORY_KEY, json)
            .apply()
    }

    fun getTracks(): List<Track>?{
        val json = sharedPref.getString(TRACKS_HISTORY_KEY, null) ?: return null
        listOfTrack = Gson().fromJson(json, Array<Track>::class.java).toMutableList()
        return listOfTrack.reversed()
    }

    private fun cropList() {
        listOfTrack = if (listOfTrack.size > 10) {
            listOfTrack.subList(0, COUNT_OF_TRACKS - 1)
        } else {
            listOfTrack
        }
    }

    fun clearList() {
        listOfTrack.clear()
        sharedPref.edit().clear().apply()
    }

}