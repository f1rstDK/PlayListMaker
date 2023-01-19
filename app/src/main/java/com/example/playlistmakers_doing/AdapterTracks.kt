package com.example.playlistmakers_doing

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import java.text.SimpleDateFormat
import java.util.*

class AdapterTracks : RecyclerView.Adapter<AdapterTracks.ListViewHolder>() {

    private var tracks = listOf<Track>()
    private var trackListener:  ((Track) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setTrackList(tracks: List<Track>) {
        this.tracks = tracks
        notifyDataSetChanged()
    }

    fun setTrackListListener (callback: ((Track) -> Unit)?) {
        trackListener = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view =  LayoutInflater.from(parent.context).inflate(R.layout.item_model, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(tracks[position])
        holder.itemView.setOnClickListener {
            val track = tracks[position]
            trackListener?.invoke(track)
        }
    }

    override fun getItemCount(): Int {
        return tracks.size
    }


    class ListViewHolder(private val view: View): RecyclerView.ViewHolder(view) {
        private val artworkUrl100: ImageView = view.findViewById(R.id.photo_track)
        private val trackArtistTime: TextView = view.findViewById(R.id.track_artist_time)
        private val trackName: TextView = view.findViewById(R.id.track_name)

        fun bind(track: Track) {
            trackName.text = track.trackName
            Glide.with(view.context)
                .load(track.artworkUrl100)
                .fitCenter()
                .transform(RoundedCorners(2))
                .placeholder(R.drawable.placeholder_for_albums)
                .dontAnimate()
                .into(artworkUrl100)
            val example = view.context.getString(R.string.for_tracks)
            trackArtistTime.text = String.format(example, convertTime(track.trackTime ?: ""), track.artistName)
        }
        private fun convertTime(trackTime: String): String = SimpleDateFormat("mm:ss", Locale.getDefault()).format(trackTime.toLong())
    }

}