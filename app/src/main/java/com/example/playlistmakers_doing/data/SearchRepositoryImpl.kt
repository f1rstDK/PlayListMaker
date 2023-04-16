package com.example.playlistmakers_doing.data

import com.example.playlistmakers_doing.data.network.NetworkClient
import com.example.playlistmakers_doing.data.other.ObservableInt
import com.example.playlistmakers_doing.data.repository.Repository

class SearchRepositoryImpl(
    private val networkHandler: NetworkClient
) : Repository {
    override fun sendRequest(text: String, observer: ObservableInt) {
        networkHandler.sendRequest(text) { screenState ->
            observer.update(screenState)
        }
    }
}