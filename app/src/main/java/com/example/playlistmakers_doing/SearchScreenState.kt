package com.example.playlistmakers_doing

sealed class SearchScreenState {
    class Result(val result: List<Track>): SearchScreenState()
    class History(val list: List<Track>) : SearchScreenState()
    object NothingFound: SearchScreenState()
    object NetworkProblem : SearchScreenState()
}