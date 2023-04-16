package com.example.playlistmakers_doing.presentation

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.example.playlistmakers_doing.data.network.NetworkClient
import com.example.playlistmakers_doing.data.shared.SharedPreferences
import com.example.playlistmakers_doing.data.shared.TrackSharedStore
import com.example.playlistmakers_doing.di.Component
import com.example.playlistmakers_doing.domain.Track
import com.example.playlistmakers_doing.presentation.other.Constants
import com.example.playlistmakers_doing.presentation.other.Constants.THEME_SWITCHER_KEY
import com.example.playlistmakers_doing.presentation.other.Constants.THEME_SWITCHER_PREFERENCES
import com.google.gson.Gson

class App : Application() {
    var darkTheme = false
    lateinit var networkHandler: NetworkClient
        private set
    lateinit var singleTrackSharedStore: TrackSharedStore
        private set
    lateinit var trackListSharedStore: SharedPreferences
        private set

    lateinit var component: Component
        private set

    private lateinit var gson: Gson

    override fun onCreate() {
        super.onCreate()
        instance = this
        switchTheme(darkTheme)
        createObjects()
    }

    private fun createObjects() {
        component = Component()
        networkHandler = NetworkClient()
        gson = Gson()
        val playerPrefs = getSharedPreferences(Constants.PLAYER_SHARED_PREFS, MODE_PRIVATE)
        singleTrackSharedStore = TrackSharedStore(playerPrefs, gson)
        val sharedPrefs = getSharedPreferences(THEME_SWITCHER_PREFERENCES, MODE_PRIVATE)
        darkTheme = sharedPrefs.getBoolean(THEME_SWITCHER_KEY, false)
        val searchPrefs = getSharedPreferences(Constants.SEARCH_TRACKS_PREFS, MODE_PRIVATE)
        trackListSharedStore = SharedPreferences(searchPrefs, gson)
    }

    fun switchTheme(darkThemeEnabled: Boolean) {
            darkTheme = darkThemeEnabled
            AppCompatDelegate.setDefaultNightMode(
                if (darkThemeEnabled) {
                    AppCompatDelegate.MODE_NIGHT_YES
                } else {
                    AppCompatDelegate.MODE_NIGHT_NO
                }
            )
    }
    companion object {
        lateinit var instance: App
            private set
    }
}