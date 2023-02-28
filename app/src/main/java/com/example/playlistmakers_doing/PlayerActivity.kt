package com.example.playlistmakers_doing

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmakers_doing.Constants.PLAYER_SHARED_PREFS
import com.example.playlistmakers_doing.Convert.convertTime
import com.example.playlistmakers_doing.Convert.getArtwork

class PlayerActivity : AppCompatActivity() {
    private val artworkView by lazy { findViewById<ImageView>(R.id.main_picture) }
    private val trackView by lazy  {findViewById<TextView>(R.id.trackTitle)}
    private val bandView by lazy {findViewById<TextView>(R.id.artist_name)}
    private val durationView by lazy  {findViewById<TextView>(R.id.duration)}
    private val collectionView by lazy { findViewById<TextView>(R.id.collectionName)}
    private val releaseDateView by lazy { findViewById<TextView>(R.id.year)}
    private val genreView by lazy { findViewById<TextView>(R.id.genre)}
    private val countryView by lazy { findViewById<TextView>(R.id.country) }


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
    }

    override fun onDestroy() {
        super.onDestroy()
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


    companion object {
        private const val EXTRA_TRACK = "EXTRA_TRACK"

        fun getIntent(context: Context, track: Track) =
            Intent(context, PlayerActivity::class.java).apply {
                putExtra(EXTRA_TRACK, track)
            }
    }
}