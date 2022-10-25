package com.example.playlistmakers_doing


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText

class ActivitySearch : AppCompatActivity() {

    lateinit var inputText: EditText
    lateinit var buttonEditText: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        inputText = findViewById(R.id.search_input_text)
        buttonEditText = findViewById(R.id.button_edit_text_searchActivity)

        inputText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                buttonEditText.visibility = clearButtonVisibility(s)

                buttonEditText.isEnabled = true
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })
        buttonEditText.setOnClickListener {
            inputText.setText("")
        }
    }
    private fun clearButtonVisibility(s: CharSequence?): Int {
        return if (s.isNullOrEmpty()) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }
    companion object{
        const val SEARCH = "SEARCH_INPUT"
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        println("Here")
        outState.putString(SEARCH, "Herl")
    }

    override fun onRestoreInstanceState(
        savedInstanceState: Bundle?,
        persistentState: PersistableBundle?
    ) {
        super.onRestoreInstanceState(savedInstanceState, persistentState)
        println("TT")
    }

}