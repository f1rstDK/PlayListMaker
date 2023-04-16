package com.example.playlistmakers_doing.data.repository


import com.example.playlistmakers_doing.data.other.ObservableInt

interface Repository {

    fun sendRequest(text: String, observer: ObservableInt)

}