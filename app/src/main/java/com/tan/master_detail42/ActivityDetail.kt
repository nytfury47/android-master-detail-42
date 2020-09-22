package com.tan.master_detail42

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*

class ActivityDetail : AppCompatActivity() {

    private var selectedTrack: Track? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_detail)

        selectedTrack = intent.getSerializableExtra(TRACK_KEY) as Track
        Picasso.with(this)
            .load(selectedTrack?.artwork)
            .placeholder(R.drawable.placeholder_image)
            .error(R.drawable.placeholder_image)
            .into(trackImageView)

        val stringId = applicationInfo.labelRes
        val appName = if (stringId == 0) applicationInfo.nonLocalizedLabel.toString() else getString(stringId)
        title = selectedTrack?.trackName ?: appName

        trackPrice?.text = String.format(getString(R.string.txt_activity_detail_track_price), selectedTrack?.price)
        trackGenre?.text = String.format(getString(R.string.txt_activity_detail_track_genre), selectedTrack?.genre)
        trackDescription?.text = selectedTrack?.description
    }

    companion object {
        private const val TRACK_KEY = "TRACK"
    }
}