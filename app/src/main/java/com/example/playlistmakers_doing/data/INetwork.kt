package com.example.playlistmakers_doing.data

import com.example.playlistmakers_doing.presentation.state.SearchScreenState

interface INetwork {
    fun sendRequest(
        inputText: String,
        resultCallback: ((SearchScreenState) -> Unit)
    )
}