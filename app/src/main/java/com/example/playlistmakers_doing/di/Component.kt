package com.example.playlistmakers_doing.di

import com.example.playlistmakers_doing.presentation.App
import com.example.playlistmakers_doing.presentation.presenter.SearchPresenter
import com.example.playlistmakers_doing.data.ViewSearch

class Component {
    fun provideSearchPresenter(viewSearch: ViewSearch) =
        SearchPresenter(
            viewSearch,
            App.instance.networkDispatcher
        )
}
