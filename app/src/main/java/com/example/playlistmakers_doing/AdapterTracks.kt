package com.example.playlistmakers_doing

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

class AdapterTracks(
    private val tracks: List<Track>
) :RecyclerView.Adapter<AdapterTracks.ListViewHolder>() {


    inner class ListViewHolder(val view: View): RecyclerView.ViewHolder(view) {
        private val photoTrack: ImageView
        private val trackArtistTime: TextView
        private val trackName: TextView

        init {
            photoTrack = view.findViewById(R.id.photo_track)
            trackArtistTime = view.findViewById(R.id.track_artist_time)
            trackName = view.findViewById(R.id.track_name)
        }
        fun bind(track: Track) {
            trackName.text = track.trackName
            Glide.with(view.context).load(track.artworkUrl100).fitCenter().transform(RoundedCorners(2)).placeholder(R.drawable.placeholder_for_albums).into(photoTrack)
            val example = view.context.getString(R.string.for_tracks)
            trackArtistTime.text = String.format(example, track.trackTime, track.artistName)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view =  LayoutInflater.from(parent.context).inflate(R.layout.item_model, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(tracks[position])
    }

    override fun getItemCount(): Int {
        return tracks.size
    }

}