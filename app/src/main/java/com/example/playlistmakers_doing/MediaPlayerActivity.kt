package com.example.playlistmakers_doing

import android.media.MediaPlayer
import kotlin.properties.Delegates

class MediaPlayerActivity {
    private val mediaPlayer = MediaPlayer()

    var stateCallback: ((PlayerState) -> Unit)? = null

    private var playState by Delegates.observable(PlayerState.DEFAULT) { _, _, newState ->
        stateCallback?.invoke(newState)
    }

    fun preparePlayer(url: String) {
        playState = PlayerState.DEFAULT
        with(mediaPlayer) {
            setDataSource(url)
            prepareAsync()
            setOnPreparedListener {
                playState = PlayerState.PREPARED
            }
            setOnCompletionListener {
                playState = PlayerState.PREPARED
            }
        }

    }
 //
    fun getCurrentPosition(): Int {
        return when (playState) {
            PlayerState.PLAYING -> mediaPlayer.currentPosition
            else -> 0
        }
    }

    fun playOrPause() {
        if (playState == PlayerState.PAUSED || playState == PlayerState.PREPARED) {
            mediaPlayer.start()
            playState = PlayerState.PLAYING
        } else if (playState == PlayerState.PLAYING) {
            mediaPlayer.pause()
            playState = PlayerState.PAUSED
        }
    }

    fun release() {
        playState = PlayerState.DEFAULT
        stateCallback = null
        mediaPlayer.release()
    }
}