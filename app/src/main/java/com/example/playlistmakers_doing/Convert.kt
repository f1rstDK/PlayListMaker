package com.example.playlistmakers_doing

import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

object Convert {
    fun convert(con: Tracks?) = Track(
        trackName = con?.trackName,
        artistName = con?.artistName,
        trackTime = con?.trackTimeMillis,
        artworkUrl100 = con?.artworkUrl100,
        collectionName = con?.collectionName,
        releaseDate = getYearFromTimestamp(con?.releaseDate),
        genre = con?.genre,
        country = con?.country
    )
    fun convertTime(time: Int?): String =
        SimpleDateFormat(TRACK_TIME_PATTERN, Locale.getDefault())
            .format(time?.toLong() ?: TRACK_TIME_IF_NULL)

    private fun getYearFromTimestamp(timestamp: String?): String {
        return if (timestamp != null) {
            LocalDateTime
                .parse(timestamp, DateTimeFormatter.ofPattern(DATETIME_PATTERN))
                .year
                .toString()
        } else DATETIME_IF_NULL
    }

    fun getArtwork(artworkUrl100: String?) =
        artworkUrl100?.replaceAfterLast('/', "512x512bb.jpg")

    private const val DATETIME_IF_NULL = ""
    private const val TRACK_TIME_IF_NULL = 0
    private const val DATETIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ssz"
    private const val TRACK_TIME_PATTERN = "mm:ss"
}