package com.example.playlistmakers_doing

class Convert {
    fun convert(con: Tracks?) = Track(
        trackName = con?.trackName.toString(),
        artistName = con?.artistName.toString(),
        trackTime = con?.trackTimeMillis.toString(),
        artworkUrl100 = con?.artWorkUrl100.toString()
    )
}