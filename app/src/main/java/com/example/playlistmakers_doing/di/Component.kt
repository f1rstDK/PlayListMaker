package com.example.playlistmakers_doing.di

import com.example.playlistmakers_doing.data.SearchRepositoryImpl
import com.example.playlistmakers_doing.data.other.ObservableInt
import com.example.playlistmakers_doing.domain.interactor.GetTrackList
import com.example.playlistmakers_doing.presentation.App
import com.example.playlistmakers_doing.presentation.presenter.SearchPresenter
import com.example.playlistmakers_doing.presentation.ui.ActivitySearch

class Component {
    fun provideGetTrackListUseCase() = GetTrackList(provideSearchRepository())
    fun provideSearchRepository() = SearchRepositoryImpl(App.instance.networkHandler)
    fun provideSearchPresenter(observer: ObservableInt) =
        SearchPresenter(
            observer,
            App.instance.trackListSharedStore,
            provideGetTrackListUseCase()
        )
}