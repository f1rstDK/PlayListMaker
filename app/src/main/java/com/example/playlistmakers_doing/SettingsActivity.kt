package com.example.playlistmakers_doing

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        val settingButton_agreement = findViewById<Button>(R.id.agreement_users)
        val settingButton_chatSuppoort = findViewById<Button>(R.id.chat_to_support)
        val settingButton_shareApp = findViewById<Button>(R.id.share_app)

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