package com.example.playlistmakers_doing

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.example.playlistmakers_doing.Constants.THEME_SWITCHER_KEY
import com.example.playlistmakers_doing.Constants.THEME_SWITCHER_PREFERENCES
import com.google.android.material.switchmaterial.SwitchMaterial

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        val settingButton_agreement = findViewById<Button>(R.id.agreement_users)
        val settingButton_chatSuppoort = findViewById<Button>(R.id.chat_to_support)
        val settingButton_shareApp = findViewById<Button>(R.id.share_app)
        val themeSwitcher = findViewById<SwitchMaterial>(R.id.theme_switcher)

        val sharedPrefs = getSharedPreferences(THEME_SWITCHER_PREFERENCES, MODE_PRIVATE)

        val darkMode = sharedPrefs.getBoolean(THEME_SWITCHER_KEY, false)

        val backToMain = findViewById<ImageButton>(R.id.back_to_main)

        backToMain.setOnClickListener {
            val mainIntent = Intent(this, MainActivity::class.java)
            startActivity(mainIntent)
        }
        themeSwitcher.isChecked = darkMode

        themeSwitcher.setOnCheckedChangeListener { switcher, checked ->
            (applicationContext as App).switchTheme(checked)
            sharedPrefs.edit()
                .putBoolean(THEME_SWITCHER_KEY, checked)
                .apply()
        }

        settingButton_shareApp.setOnClickListener {
            val mIntent = Intent(Intent.ACTION_SEND).apply {
                type = "*/*"
                putExtra(Intent.EXTRA_TEXT, getString(R.string.link_practicum_developer))
            }
            if(intent.resolveActivity(packageManager)!= null) {
                startActivity(mIntent)
            }
        }

        settingButton_chatSuppoort.setOnClickListener {
            val chatIntent = Intent(this, ShareActivity::class.java)
            startActivity(chatIntent)
        }

        settingButton_agreement.setOnClickListener {
            val browseAgreement_intent = Intent(Intent.ACTION_VIEW,
            Uri.parse(getString(R.string.oferta)))
            startActivity(browseAgreement_intent)
        }

    }
}