package com.tan.master_detail42

import android.app.Activity
import android.net.Uri.Builder
import okhttp3.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.util.ArrayList

/**
 * Class for handling the search API request
 */
class TrackRequester(listeningActivity: Activity) {

    interface TrackRequesterResponse {
        fun receivedNewTrackList(newTrackList: ArrayList<Track>)
    }

    private val responseListener: TrackRequesterResponse
    private val client: OkHttpClient

    init {
        responseListener = listeningActivity as TrackRequesterResponse
        client = OkHttpClient()
    }

    /**
     * Handle search api request and response
     */
    fun getTrack() {
        var resultList: JSONArray
        val trackList = ArrayList<Track>()

        val urlRequest = Builder().scheme(URL_SCHEME)
            .authority(URL_AUTHORITY)
            .appendPath(URL_PATH)
            .appendQueryParameter(URL_QUERY_PARAM_TERM, URL_QUERY_PARAM_TERM_VAL)
            .appendQueryParameter(URL_QUERY_PARAM_COUNTRY, URL_QUERY_PARAM_COUNTRY_VAL)
            .appendQueryParameter(URL_QUERY_PARAM_MEDIA, URL_QUERY_PARAM_MEDIA_VAL)
            .build().toString()
        val request = Request.Builder().url(urlRequest).build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                responseListener.receivedNewTrackList(trackList)
            }
            override fun onResponse(call: Call, response: Response) {
                try {
                    val responseJSON = JSONObject(response.body()!!.string())
                    resultList = responseJSON.getJSONArray(TRACK_LIST)

                    for (i in 0 until resultList.length()) {
                        val item = resultList.getJSONObject(i)
                        trackList.add(Track(item))
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }

                responseListener.receivedNewTrackList(trackList)
            }
        })
    }

    companion object {
        // iTunes Search API: https://itunes.apple.com/search?term=star&amp;country=au&amp;media=movie&amp;all
        private const val URL_SCHEME = "https"
        private const val URL_AUTHORITY = "itunes.apple.com"
        private const val URL_PATH = "search"
        private const val URL_QUERY_PARAM_TERM = "term"
        private const val URL_QUERY_PARAM_COUNTRY = "country"
        private const val URL_QUERY_PARAM_MEDIA = "media"

        private const val URL_QUERY_PARAM_TERM_VAL = "star"
        private const val URL_QUERY_PARAM_COUNTRY_VAL = "au"
        private const val URL_QUERY_PARAM_MEDIA_VAL = "movie"

        // Target JSONArray for trackList
        private const val TRACK_LIST = "results"
    }
}