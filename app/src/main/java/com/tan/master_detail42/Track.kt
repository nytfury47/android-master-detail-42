package com.tan.master_detail42

import org.json.JSONException
import org.json.JSONObject
import java.io.Serializable

class Track(trackJSON: JSONObject) : Serializable {

    lateinit var trackName: String
        private set
    lateinit var artwork: String
        private set
    lateinit var price: String
        private set
    lateinit var genre: String
        private set
    lateinit var description: String
        private set

    init {
        try {
            trackName = trackJSON.getString(TRACK_NAME)
            artwork = trackJSON.getString(ARTWORK)
            price = trackJSON.getDouble(PRICE).toString()
            genre = trackJSON.getString(GENRE)
            description = trackJSON.getString(DESCRIPTION)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    companion object {
        private const val TRACK_NAME = "trackName"
        private const val ARTWORK = "artworkUrl100"
        private const val PRICE = "trackPrice"
        private const val GENRE = "primaryGenreName"
        private const val DESCRIPTION = "longDescription"
    }
}