package com.example.playlistmakers_doing.data.network

import com.example.playlistmakers_doing.data.models.ApiMain
import com.example.playlistmakers_doing.data.models.ApiResponseApp
import com.example.playlistmakers_doing.data.other.Convert
import com.example.playlistmakers_doing.presentation.state.SearchScreenState
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

interface INetworkClient {
    fun sendRequest(
        inputText: String,
        resultCallback: ((SearchScreenState) -> Unit)
    )
}
