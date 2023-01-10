package com.example.playlistmakers_doing

sealed class SearchScreenState {
    class Result(val list: List<Track>): SearchScreenState()
    object NothingFound: SearchScreenState()
    object NetworkProblem : SearchScreenState()
}