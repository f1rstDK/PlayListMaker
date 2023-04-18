package com.example.playlistmakers_doing.presentation.presenter

import com.example.playlistmakers_doing.data.INetwork
import com.example.playlistmakers_doing.data.ViewSearch
import com.example.playlistmakers_doing.domain.Network
import com.example.playlistmakers_doing.presentation.state.SearchScreenState

class SearchPresenter(
    private val viewSearch: ViewSearch,
    private val network: Network
): INetwork{
    override fun sendRequest(inputText: String, resultCallback: (SearchScreenState) -> Unit) {
        network.sendRequest(inputText) {
            screenState -> viewSearch.setScreenState(screenState)
        }
    }
}