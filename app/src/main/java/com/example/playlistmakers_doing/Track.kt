package com.example.playlistmakers_doing

import java.io.Serializable

data class Track (
    val trackName: String?, // Название композиции
    val artistName: String?, // Имя исполнителя
    val trackTime: Int?, // Продолжительность трека
    val artworkUrl100: String?  , // Ссылка на изображение обложки
    val collectionName: String?, //Название альбома
    val releaseDate: String?, // Год релиза трека
    val genre: String?, // Жанр трека
    val country: String?, // Страна исполнителя
    val previewUrl: String?
) : Serializable