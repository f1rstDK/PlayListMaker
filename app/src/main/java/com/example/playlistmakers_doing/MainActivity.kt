package com.example.playlistmakers_doing

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val settings_button = findViewById<Button>(R.id.setting)

        settings_button.setOnClickListener {
            val setting_intent = Intent(this,SettingsActivity::class.java)
            startActivity(setting_intent)
        } //
        val activity_search = findViewById<Button>(R.id.search)

        activity_search.setOnClickListener {
            val search_intent = Intent(this, com.example.playlistmakers_doing.ActivitySearch::class.java)
            startActivity(search_intent)
        }

        val mediateka_button =findViewById<Button>(R.id.mediaLibrary)

        mediateka_button.setOnClickListener {
            val mediateka_intent = Intent(this, MediaTeka::class.java)
            startActivity(mediateka_intent)
        }
    }
}