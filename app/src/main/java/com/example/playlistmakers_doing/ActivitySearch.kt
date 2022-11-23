package com.example.playlistmakers_doing


import android.content.Context
import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ActivitySearch : AppCompatActivity() {

    lateinit var inputText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        inputText = findViewById(R.id.search_input_text)
        val track_1 = Track(
            "Smells Like Teen Spirit",
            "Nirvana",
            "5:01",
            "https://is5-ssl.mzstatic.com/image/thumb/Music115/v4/7b/58/c2/7b58c21a-2b51-2bb2-e59a-9bb9b96ad8c3/00602567924166.rgb.jpg/100x100bb.jpg"
        )
        val track_2 = Track(
            "Billie Jean",
           "Michael Jackson",
            "4:35",
         "https://is5-ssl.mzstatic.com/image/thumb/Music125/v4/3d/9d/38/3d9d3811-71f0-3a0e-1ada-3004e56ff852/827969428726.jpg/100x100bb.jpg"
        )
        val track_3 = Track(
         "Stayin' Alive",
            "Bee Gees",
           "4:10",
            "https://is4-ssl.mzstatic.com/image/thumb/Music115/v4/1f/80/1f/1f801fc1-8c0f-ea3e-d3e5-387c6619619e/16UMGIM86640.rgb.jpg/100x100bb.jpg"
        )
        val track_4 = Track(
           "Whole Lotta Love",
          "Led Zeppelin",
         "5:33",
             "https://is2-ssl.mzstatic.com/image/thumb/Music62/v4/7e/17/e3/7e17e33f-2efa-2a36-e916-7f808576cf6b/mzm.fyigqcbs.jpg/100x100bb.jpg"
        )
        val track_5 = Track(
            "Sweet Child O'Mine",
             "Guns N' Roses",
            " 5:03",
           "https://is5-ssl.mzstatic.com/image/thumb/Music125/v4/a0/4d/c4/a04dc484-03cc-02aa-fa82-5334fcb4bc16/18UMGIM24878.rgb.jpg/100x100bb.jpg"
        )
        val listOfTracks = listOf(track_1,track_2,track_3,track_4,track_5)
        val adapter = AdapterTracks(listOfTracks)
        val recyclerView = findViewById<RecyclerView>(R.id.RecyclerForTracks)
        recyclerView.adapter = adapter
        inputText = findViewById(R.id.search_input_text)
        val clearButton = findViewById<ImageView>(R.id.clearIcon)
        inputText.requestFocus()
        hideKeyboard(inputText)

        inputText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
        clearButton.setOnClickListener {
            inputText.text.clear()
            hideKeyboard(inputText)
        }

    }
    private fun hideKeyboard(view: View) {
        val inputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        inputMethodManager?.hideSoftInputFromWindow(view.windowToken, 0)
    }
    companion object {
        private const val EDIT_TEXT_KEY = "EDIT_TEXT_KEY"
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val editTextString = inputText.text.toString()
        outState.putString(EDIT_TEXT_KEY, editTextString)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val editTextString = savedInstanceState.getString(EDIT_TEXT_KEY, "")
        inputText.setText(editTextString, TextView.BufferType.EDITABLE)
    }
}