package com.example.playlistmakers_doing.presentation

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.example.playlistmakers_doing.domain.Network
import com.example.playlistmakers_doing.presentation.other.Constants.THEME_SWITCHER_KEY
import com.example.playlistmakers_doing.presentation.other.Constants.THEME_SWITCHER_PREFERENCES

class App : Application() {
    var darkTheme = false
    lateinit var networkDispatcher: Network
        private set

    override fun onCreate() {
        super.onCreate()

        instance = this

        networkDispatcher = Network()
        val sharedPrefs = getSharedPreferences(THEME_SWITCHER_PREFERENCES, MODE_PRIVATE)
        darkTheme = sharedPrefs.getBoolean(THEME_SWITCHER_KEY, false)
        switchTheme(darkTheme)
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
