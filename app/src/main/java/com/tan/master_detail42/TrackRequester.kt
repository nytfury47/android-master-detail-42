package com.tan.master_detail42

import android.app.Activity
import android.content.Context
import android.net.Uri.Builder
import okhttp3.*
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.util.ArrayList

class TrackRequester(listeningActivity: Activity) {

    interface TrackRequesterResponse {
        fun receivedNewTrackList(newTrackList: ArrayList<Track>)
    }

    private val responseListener: TrackRequesterResponse
    private val context: Context
    private val client: OkHttpClient
    var isLoadingData: Boolean = false
        private set

    init {
        responseListener = listeningActivity as TrackRequesterResponse
        context = listeningActivity.applicationContext
        client = OkHttpClient()
    }

    fun getTrack() {
        val urlRequest = Builder().scheme(URL_SCHEME)
            .authority(URL_AUTHORITY)
            .appendPath(URL_PATH)
            .appendQueryParameter(URL_QUERY_PARAM_TERM, context.getString(R.string.url_query_param_term))
            .appendQueryParameter(URL_QUERY_PARAM_COUNTRY, context.getString(R.string.url_query_param_country))
            .appendQueryParameter(URL_QUERY_PARAM_MEDIA, context.getString(R.string.url_query_param_media))
            .build().toString()
        val request = Request.Builder().url(urlRequest).build()

        isLoadingData = true

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                isLoadingData = false
                e.printStackTrace()
            }
            override fun onResponse(call: Call, response: Response) {
                try {
                    val responseJSON = JSONObject(response.body()!!.string())
                    val resultList = responseJSON.getJSONArray(TRACK_LIST)
                    val trackList: ArrayList<Track> = ArrayList()

                    for (i in 0 until resultList.length()) {
                        val item = resultList.getJSONObject(i)
                        trackList.add(Track(item))
                    }

                    responseListener.receivedNewTrackList(trackList)
                    isLoadingData = false
                } catch (e: JSONException) {
                    isLoadingData = false
                    e.printStackTrace()
                }
            }
        })
    }

    companion object {
        private const val URL_SCHEME = "https"
        private const val URL_AUTHORITY = "itunes.apple.com"
        private const val URL_PATH = "search"
        private const val URL_QUERY_PARAM_TERM = "term"
        private const val URL_QUERY_PARAM_COUNTRY = "country"
        private const val URL_QUERY_PARAM_MEDIA = "media"
        private const val TRACK_LIST = "results"
    }
}