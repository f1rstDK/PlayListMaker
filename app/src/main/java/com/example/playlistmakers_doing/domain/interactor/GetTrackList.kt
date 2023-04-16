package com.example.playlistmakers_doing.domain.interactor

import com.example.playlistmakers_doing.data.other.IObservable
import com.example.playlistmakers_doing.data.other.ObservableInt
import com.example.playlistmakers_doing.data.repository.Repository

class GetTrackList(
    private val repository: Repository
) : IObservable{
    override val observers: ArrayList<ObservableInt> = ArrayList()

    fun execute(text: String, observer: ObservableInt) {
        repository.sendRequest(text, observer)
    }
}