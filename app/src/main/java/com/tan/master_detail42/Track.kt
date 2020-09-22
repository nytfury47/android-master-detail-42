package com.tan.master_detail42

import org.json.JSONException
import org.json.JSONObject
import java.io.Serializable

class Track(trackJSON: JSONObject) : Serializable {

    var trackName: String = TRACK_NAME
        private set
    var artwork: String = TRACK_IMAGE
        private set
    var price: String = TRACK_PRICE
        private set
    var genre: String = TRACK_GENRE
        private set
    var description: String = TRACK_DESCRIPTION
        private set

    init {
        try {
            trackName = trackJSON.getString(NAME)
            artwork = trackJSON.getString(ARTWORK)
            price = trackJSON.getDouble(PRICE).toString()
            genre = trackJSON.getString(GENRE)
            description = trackJSON.getString(DESCRIPTION)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    companion object {
        // trackJSON keys
        private const val NAME = "trackName"
        private const val ARTWORK = "artworkUrl100"
        private const val PRICE = "trackPrice"
        private const val GENRE = "primaryGenreName"
        private const val DESCRIPTION = "longDescription"

        // Track object default values
        private const val TRACK_NAME = "Track Name"
        private const val TRACK_IMAGE = "Track Image"
        private const val TRACK_PRICE = "Track Price"
        private const val TRACK_GENRE = "Track Genre"
        private const val TRACK_DESCRIPTION = "Track Description"
    }
}