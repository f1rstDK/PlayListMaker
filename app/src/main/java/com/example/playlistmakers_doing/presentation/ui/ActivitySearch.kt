package com.example.playlistmakers_doing.presentation.ui


import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.core.view.isEmpty
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmakers_doing.*
import com.example.playlistmakers_doing.data.other.ObservableInt
import com.example.playlistmakers_doing.data.shared.SharedPreferences
import com.example.playlistmakers_doing.domain.Track
import com.example.playlistmakers_doing.presentation.App
import com.example.playlistmakers_doing.presentation.presenter.SearchPresenter
import com.example.playlistmakers_doing.presentation.recycler.AdapterTracks
import com.example.playlistmakers_doing.presentation.state.SearchScreenState

class ActivitySearch : AppCompatActivity(), ObservableInt {

    lateinit var inputText: EditText
    lateinit var recyclerView: RecyclerView
    lateinit var networkError: LinearLayout
    lateinit var nothingFound: LinearLayout
    lateinit var adapterTracks: AdapterTracks
    lateinit var historyAdapter: AdapterTracks
    lateinit var buttonUpdate: Button
    lateinit var textInputLayout: LinearLayout
    lateinit var btnClearHistory: Button
    lateinit var linerHistory: RelativeLayout
    lateinit var recyclerHistory: RecyclerView
    lateinit var sharedStore: SharedPreferences
    lateinit var progressBarLiner: RelativeLayout
    lateinit var searchPresenter: SearchPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        initObject()
        setView()
        setListener()
        setAdapter()

        if(recyclerHistory.isEmpty()) {
            setAllInvisible()
        }

        val buttonBackToMain = findViewById<ImageView>(R.id.back_to_main)

        buttonBackToMain.setOnClickListener {
            finish()
        }

        btnClearHistory.setOnClickListener {
            sharedStore.clearList()
            val emptyList = mutableListOf<Track>()
            setScreenState(SearchScreenState.History(emptyList))
            setAllInvisible()
        }

        ///
        val clearButton = findViewById<ImageView>(R.id.clearIcon)
        inputText.setOnEditorActionListener{_, actionId, _ ->
            setAllInvisible()
            if (actionId == EditorInfo.IME_ACTION_DONE && inputText.text.isNotEmpty()) {
                sendRequest()
                true
            } else false
        }

        inputText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                val list = searchPresenter.getListFromSharedPrefs()
                list?.let {
                    setScreenState(SearchScreenState.History(it))
                }
            }
        }
        inputText.requestFocus()
        hideKeyboard(inputText)

        inputText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                clearButton.visibility = View.GONE
            }

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                clearButton.visibility = View.VISIBLE
                searchDebounce()
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
        clearButton.setOnClickListener {
            inputText.text.clear()
            hideKeyboard(inputText)
            setAllInvisible()
            linerHistory.visibility = View.VISIBLE
            clearButton.visibility = View.GONE
        }
    }

    private fun setListener() {
        buttonUpdate.setOnClickListener {
            setAllInvisible()
            sendRequest()
        }
    }

    private fun setAdapter() {
        recyclerView.adapter = adapterTracks
        recyclerHistory.adapter = historyAdapter
    }


    private fun initObject() {
        val component = App.instance.component
        searchPresenter = component.provideSearchPresenter(this)
        adapterTracks = AdapterTracks()
        historyAdapter = AdapterTracks()
    }

    private fun setView() {
        progressBarLiner = findViewById(R.id.progressBarLiner)
        recyclerView =  findViewById(R.id.RecyclerForTracks)
        inputText = findViewById(R.id.search_input_text)
        networkError = findViewById(R.id.networkTrouble)
        nothingFound = findViewById(R.id.nothing_found)
        buttonUpdate = findViewById(R.id.btn_update)
        textInputLayout = findViewById(R.id.til_search)
        linerHistory = findViewById(R.id.liner_history)
        btnClearHistory = findViewById(R.id.btn_clear_history)
        recyclerHistory = findViewById(R.id.historyRecyclerView)
    }

    private fun visibleProgressBar(input: Boolean) {
        if(input) {
            progressBarLiner.visibility = View.GONE
        } else {
            progressBarLiner.visibility = View.VISIBLE
        }
    }

    private fun sendRequest() {
        var request = inputText.text.toString()
        setScreenState(SearchScreenState.Loading)
        searchPresenter.sendRequest(request)
        //network.sendRequest(request, this::setScreenState)
    }

    private fun hideKeyboard(view: View) {
        val inputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        inputMethodManager?.hideSoftInputFromWindow(view.windowToken, 0)
    }
    fun setScreenState(state: SearchScreenState) {
        setAllInvisible()
        when(state) {
            is SearchScreenState.Result -> {
                recyclerView.visibility = View.VISIBLE
                adapterTracks.setTrackList(state.result)
                adapterTracks.setTrackListListener {
                    track -> sharedStore.addToList(track)
                    if(clickDebounce()) {
                        PlayerActivity.getIntent(this, track).apply { startActivity(this) }
                    }
                }
            }
            is SearchScreenState.NetworkProblem -> {
                visibleProgressBar(true)
                networkError.visibility = View.VISIBLE
            }
            is SearchScreenState.NothingFound -> {
                nothingFound.visibility = View.VISIBLE
            }
            is SearchScreenState.History -> {
                visibleProgressBar(true)
                linerHistory.visibility = View.VISIBLE
                historyAdapter.setTrackList(state.list)
                historyAdapter.setTrackListListener { track ->
                    if (clickDebounce()) {
                        PlayerActivity.getIntent(this, track).apply {
                            startActivity(this)
                        }
                    }
                }
            }
            is SearchScreenState.Loading -> {
                setAllInvisible()
                visibleProgressBar(false)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        searchPresenter.onViewDestroy()
    }

    private fun setAllInvisible() {
        recyclerView.visibility = View.GONE
        networkError.visibility = View.GONE
        nothingFound.visibility = View.GONE
        linerHistory.visibility = View.GONE
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

    companion object {
        private const val EDIT_TEXT_KEY = "EDIT_TEXT_KEY"
        private const val CLICK_DEBOUNCE_DELAY = 1000L
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
    }

    private var isClickAllowed = true
    private val handler = Handler(Looper.getMainLooper())
    private val searchRunnable = Runnable { sendRequest() }

    private fun searchDebounce() {
        handler.removeCallbacks(searchRunnable)
        handler.postDelayed(searchRunnable, SEARCH_DEBOUNCE_DELAY)
    }

    private fun clickDebounce(): Boolean {
        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            handler.postDelayed({ isClickAllowed = true}, CLICK_DEBOUNCE_DELAY)
        }
        return current
    }

    override fun update(data: Any) {
        val state = data as SearchScreenState
        setScreenState(state)
    }
}