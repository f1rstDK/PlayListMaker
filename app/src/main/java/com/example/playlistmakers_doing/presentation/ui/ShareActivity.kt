package com.example.playlistmakers_doing.presentation.ui

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.playlistmakers_doing.R

class ShareActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_share)

       val btnEditText = findViewById<Button>(R.id.buttonEditText)
       val editTextMail = findViewById<EditText>(R.id.email_chat_support)
       val editTextSubject = findViewById<EditText>(R.id.chat_support_theme)
       val editTextMessage = findViewById<EditText>(R.id.chat_support_message)

        btnEditText.setOnClickListener {
            val recipient = editTextMail.text.toString().trim()
            val subject = editTextSubject.text.toString().trim()
            val message = editTextMessage.text.toString().trim()
            
            sendMail(recipient, subject, message)
        }
    }

    private fun sendMail(recipient: String, subject: String, message: String) {
            val mIntent = Intent(Intent.ACTION_SEND)
            mIntent.data = Uri.parse("mail to:")
            mIntent.type = "text/plain"
            mIntent.putExtra(Intent.EXTRA_EMAIL, getString(R.string.stores_steamcommunity_Mail_ru))
            mIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.chat_to_support_theme))
            mIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.chat_to_support_message))

        try {
            startActivity(Intent.createChooser(mIntent, "Choose Email Client..."))
        }
        catch (e: Exception){
            Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
        }
    }
}