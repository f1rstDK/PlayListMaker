package com.example.playlistmakers_doing

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.playlistmakers_doing.Constants
import com.example.playlistmakers_doing.Constants.THEME_SWITCHER_KEY
import com.example.playlistmakers_doing.Constants.THEME_SWITCHER_PREFERENCES

class App : Application() {
    var darkTheme = false
    override fun onCreate() {
        super.onCreate()

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
}