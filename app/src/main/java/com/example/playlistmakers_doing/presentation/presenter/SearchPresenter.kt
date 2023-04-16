package com.example.playlistmakers_doing.presentation.presenter

import com.example.playlistmakers_doing.data.other.ObservableInt
import com.example.playlistmakers_doing.data.shared.SharedPreferences
import com.example.playlistmakers_doing.data.shared.TrackSharedStore
import com.example.playlistmakers_doing.domain.Track
import com.example.playlistmakers_doing.domain.interactor.GetTrackList
import com.example.playlistmakers_doing.presentation.ui.ActivitySearch

class SearchPresenter(
    private var observer: ObservableInt? = null,
    private val sharedStore: SharedPreferences,
    private val getTrackListUseCase: GetTrackList
) {
    init {
        observer?.let { obs ->
            getTrackListUseCase.subscribe(obs)
        }
    }
    fun addToSharedPrefs(track: Track) {
        sharedStore.addToList(track)
    } // later
    fun getListFromSharedPrefs(): List<Track>? = sharedStore.getTracks()

    fun clearSharedPrefs() = sharedStore.clearList() // later
    fun sendRequest(text: String) {
        getTrackListUseCase.execute(text) { stateAny ->
            observer?.update(stateAny)
        }
    }

    fun onViewDestroy() {
        observer = null
    }

}