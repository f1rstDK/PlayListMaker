package com.example.playlistmakers_doing


import android.content.Context
import retrofit2.Callback
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Response

class ActivitySearch : AppCompatActivity() {

    lateinit var inputText: EditText
    lateinit var recyclerView: RecyclerView
    lateinit var networkError: LinearLayout
    lateinit var nothingFound: LinearLayout
    lateinit var adapterTracks: AdapterTracks
    lateinit var buttonUpdate: Button
    lateinit var textInputLayout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        setView()
        setAdapter()

        buttonUpdate.setOnClickListener{
            setAllInvisible()
            sendRequest()
        }

        val clearButton = findViewById<ImageView>(R.id.clearIcon)
        inputText.requestFocus()
        hideKeyboard(inputText)
        inputText.setOnEditorActionListener{_, actionId, _ ->
            setAllInvisible()
            if (actionId == EditorInfo.IME_ACTION_DONE && inputText.text.isNotEmpty()) {
                sendRequest()
                true
            } else false
        }
        inputText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                clearButton.visibility = View.GONE
            }

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                clearButton.visibility = View.VISIBLE
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
        clearButton.setOnClickListener {
            inputText.text.clear()
            hideKeyboard(inputText)
            setAllInvisible()
            clearButton.visibility = View.GONE
        }
    }

    private fun setAdapter() {
        adapterTracks = AdapterTracks()
        recyclerView.adapter = adapterTracks
    }

    private fun setView() {
        recyclerView =  findViewById(R.id.RecyclerForTracks)
        inputText = findViewById(R.id.search_input_text)
        networkError = findViewById(R.id.networkTrouble)
        nothingFound = findViewById(R.id.nothing_found)
        buttonUpdate = findViewById(R.id.btn_update)
        textInputLayout = findViewById(R.id.til_search)
    }

    private fun sendRequest() {
        ApiMain.apiaService.search(inputText.text.toString())
            .enqueue(object : Callback<ApiResponseApp> {
                override fun onResponse(
                    call: Call<ApiResponseApp>,
                    response: Response<ApiResponseApp>
            ) {
                when {
                    response.code() == 200 -> {
                        if(response.body()?.resultCount != 0) {
                            val convert = Convert()
                            response.body()
                                ?.results
                                ?.map {convert.convert(it)}
                                ?.apply {  setScreen(SearchScreenState.Result(this)) }
                        } else {
                            setScreen(SearchScreenState.NothingFound)
                            // вот сюда надо написать
                        }
                        }
                    response.code() in 400..599 -> {
                       setScreen(SearchScreenState.NetworkProblem)
                    }
                }
                Log.d("TAG", "${response.body()}")
            }

                override fun onFailure(call: Call<ApiResponseApp>, t: Throwable) {
                    setScreen(SearchScreenState.NetworkProblem)
                    Log.d("TAG", "${t.stackTrace}")
                }
            })
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

    private fun setScreen(state: SearchScreenState) {
        setAllInvisible()
        when(state) {
            is SearchScreenState.Result -> {
                recyclerView.visibility = View.VISIBLE
                adapterTracks.setTrackList(state.list)
            }
            is SearchScreenState.NetworkProblem -> {
                networkError.visibility = View.VISIBLE
            }
            is SearchScreenState.NothingFound -> {
                nothingFound.visibility = View.VISIBLE
            }
        }
    }
    private fun setAllInvisible() {
        recyclerView.visibility = View.GONE
        networkError.visibility = View.GONE
        nothingFound.visibility = View.GONE
    }
}