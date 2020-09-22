package com.tan.master_detail42

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*

/**
 * Activity class for the detailed view of each track item
 */
class ActivityDetail : AppCompatActivity() {

    private var selectedTrack: Track? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_detail)

        // get track info
        selectedTrack = intent.getSerializableExtra(TRACK_KEY) as Track

        // track image
        Picasso.with(this)
            .load(selectedTrack?.artwork)
            .placeholder(R.drawable.placeholder_image)
            .error(R.drawable.placeholder_image)
            .into(trackImageView)

        // track name
        val stringId = applicationInfo.labelRes
        val appName = if (stringId == 0) applicationInfo.nonLocalizedLabel.toString() else getString(stringId)
        title = selectedTrack?.trackName ?: appName

        // track price, genre, long description
        trackPrice?.text = String.format(getString(R.string.txt_activity_detail_track_price), selectedTrack?.price)
        trackGenre?.text = String.format(getString(R.string.txt_activity_detail_track_genre), selectedTrack?.genre)
        trackDescription?.text = selectedTrack?.description
    }

    companion object {
        private const val TRACK_KEY = "TRACK"
    }
}