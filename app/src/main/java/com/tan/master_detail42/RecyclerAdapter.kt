package com.tan.master_detail42

import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.recyclerview_item_row.view.*

class RecyclerAdapter(
    private val tracks: ArrayList<Track>
) : RecyclerView.Adapter<RecyclerAdapter.TrackHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackHolder {
        val inflatedView = parent.inflate(R.layout.recyclerview_item_row, false)
        return TrackHolder(inflatedView)
    }

    override fun getItemCount() = tracks.size

    override fun onBindViewHolder(holder: TrackHolder, position: Int) {
        val track = tracks[position]
        holder.bindTrack(track)
    }

    class TrackHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {
        private var view: View = v
        private var track: Track? = null

        init {
            v.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            val context = itemView.context
            val showTrackIntent = Intent(context, ActivityDetail::class.java)
            showTrackIntent.putExtra(TRACK_KEY, track)
            context.startActivity(showTrackIntent)
        }

        fun bindTrack(track: Track) {
            this.track = track

            Picasso.with(view.context).load(track.artwork).into(view.itemImage)
            view.itemName.text = track.trackName
            view.itemGenre.text = track.genre
            view.itemPrice.text = String.format(view.context.getString(R.string.txt_activity_master_track_price), track.price)
        }

        companion object {
            private const val TRACK_KEY = "TRACK"
        }
    }
}