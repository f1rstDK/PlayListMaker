package com.example.playlistmakers_doing.presentation.ui

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmakers_doing.presentation.other.Constants.PLAYER_SHARED_PREFS
import com.example.playlistmakers_doing.presentation.other.Convert.convertTime
import com.example.playlistmakers_doing.presentation.other.Convert.getArtwork
import com.example.playlistmakers_doing.R
import com.example.playlistmakers_doing.presentation.domain.Track
import com.example.playlistmakers_doing.data.player.MediaPlayerActivity
import com.example.playlistmakers_doing.data.player.PlayerState
import com.example.playlistmakers_doing.data.shared.TrackSharedStore
import java.text.SimpleDateFormat
import java.util.*

class PlayerActivity: AppCompatActivity() {
    private val artworkView by lazy { findViewById<ImageView>(R.id.main_picture) }
    private val trackView by lazy  {findViewById<TextView>(R.id.trackTitle)}
    private val bandView by lazy {findViewById<TextView>(R.id.artist_name)}
    private val durationView by lazy  {findViewById<TextView>(R.id.duration)}
    private val collectionView by lazy { findViewById<TextView>(R.id.collectionName)}
    private val releaseDateView by lazy { findViewById<TextView>(R.id.year)}
    private val genreView by lazy { findViewById<TextView>(R.id.genre)}
    private val countryView by lazy { findViewById<TextView>(R.id.country) }
    private val playButton by lazy { findViewById<ImageView>(R.id.play_button) }
    private val handler = Handler(Looper.getMainLooper())
    private val timerView by lazy { findViewById<TextView>(R.id.time_remained) }
    private var mediaPlayer = MediaPlayerActivity()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_audio_player)

        val backToMain = findViewById<ImageView>(R.id.back_to_main)
        backToMain.setOnClickListener {
            finish()
        }
        val trackSharedStore =
            TrackSharedStore(getSharedPreferences(PLAYER_SHARED_PREFS, MODE_PRIVATE))
        val track = if (intent.extras != null) {
            (intent.extras!!.getSerializable(EXTRA_TRACK) as Track).apply {
                trackSharedStore.saveToSharedPrefs(this)
            }
        } else {
            trackSharedStore.loadFromSharedPrefs()
                ?: throw RuntimeException("Track can not be null!!!")
        }
        bindTrack(track)
        //Проверка на то, чтобы previewUrl не был null и приложение не крашилось
        if (track.previewUrl != null) mediaPlayer.preparePlayer(track.previewUrl)
        playButton.setOnClickListener {
            mediaPlayer.playOrPause()
        }
        //Управление состояними медиаплеером
        mediaPlayer.stateCallback = { playerState ->
            when (playerState) {
                PlayerState.DEFAULT -> {}
                PlayerState.PREPARED -> reset()
                PlayerState.PLAYING -> play()
                PlayerState.PAUSED -> pause()
            }
        }
     }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }

    private fun bindTrack(track: Track) {
        with(track) {
            Glide.with(this@PlayerActivity)
                .load(getArtwork(this.artworkUrl100))
                .centerCrop()
                .transform(RoundedCorners(8))
                .placeholder(R.drawable.placeholder_for_albums)
                .into(artworkView)
            trackView.text = trackName
            bandView.text = artistName
            durationView.text = convertTime(trackTime)
            collectionView.text = collectionName
            releaseDateView.text = releaseDate
            genreView.text = genre
            countryView.text = country
        } }

    private val runnableTimer = object : Runnable {
        override fun run() {
            timerView.text = SimpleDateFormat("mm:ss", Locale.getDefault())
                .format(mediaPlayer.getCurrentPosition())
            log(mediaPlayer.getCurrentPosition().toString())
            handler.postDelayed(this, 200L)
        }
    }

    //Проигрывание песни, смена "Играть" на "Пауза"
    private fun play() {
        playButton.setImageResource((R.drawable.ic_pause))
        handler.post(runnableTimer)
    }
    //Пауза. Смена "Пауза" на "Играть"
    private fun pause() {
        playButton.setImageResource(R.drawable.ic_play_button)
        handler.removeCallbacksAndMessages(null)
    }
    //Установка "Играть" на кнопку
    private fun reset() {
        playButton.setImageResource(R.drawable.ic_play_button)
    }

    companion object {
        private const val EXTRA_TRACK = "EXTRA_TRACK"

        fun getIntent(context: Context, track: Track) =
            Intent(context, PlayerActivity::class.java).apply {
                putExtra(EXTRA_TRACK, track)
            }
        fun log(s: String) {
            Log.d("${this::class.qualifiedName}TAG", s)
        }
    }
}