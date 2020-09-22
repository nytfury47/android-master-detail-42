package com.tan.master_detail42

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_master.*
import java.io.IOException
import java.util.ArrayList
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Handler

class ActivityMaster : AppCompatActivity(), TrackRequester.TrackRequesterResponse {

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var gridLayoutManager: GridLayoutManager
    private lateinit var adapter: RecyclerAdapter
    private var trackList: ArrayList<Track> = ArrayList()
    private lateinit var trackRequester: TrackRequester
    private var doubleBackToExitPressedOnce = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_master)

        title = getString(R.string.main_activity_title) + "(${trackList.size})"

        linearLayoutManager = LinearLayoutManager(this)
        gridLayoutManager = GridLayoutManager(this, 3)

        recyclerView.layoutManager = gridLayoutManager
        adapter = RecyclerAdapter(trackList)
        recyclerView.adapter = adapter

        trackRequester = TrackRequester(this)
    }

    override fun onStart() {
        super.onStart()
        if (trackList.size == 0) {
            requestTrack()
        }
    }

    override fun onResume() {
        super.onResume()

        if (!isInternetAvailable()) Toast.makeText(this, R.string.check_internet, Toast.LENGTH_LONG).show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_change_recycler_manager) {
            changeLayoutManager()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }

        doubleBackToExitPressedOnce = true
        Toast.makeText(this, R.string.toast_a_backToExit, Toast.LENGTH_SHORT).show()
        Handler().postDelayed( { doubleBackToExitPressedOnce = false }, DELAY_EXIT)
    }

    override fun receivedNewTrackList(newTrackList: ArrayList<Track>) {
        runOnUiThread {
            progressBar.visibility = View.INVISIBLE

            for (index in newTrackList.indices) {
                trackList.add(newTrackList[index])
                adapter.notifyItemInserted(trackList.size - 1)
            }

            title = getString(R.string.main_activity_title) + "(${trackList.size})"

            // No results
            if (newTrackList.isEmpty()) {
                Toast.makeText(this, R.string.no_result, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun changeLayoutManager() {
        if (recyclerView.layoutManager == linearLayoutManager) {
            recyclerView.layoutManager = gridLayoutManager
        } else {
            recyclerView.layoutManager = linearLayoutManager
        }
    }

    private fun requestTrack() {
        try {
            progressBar.visibility = View.VISIBLE
            trackRequester.getTrack()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    @Suppress("DEPRECATION")
    private fun isInternetAvailable(): Boolean {
        var result = false
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            cm.run {
                cm.getNetworkCapabilities(cm.activeNetwork)?.run {
                    result = when {
                        hasTransport(NetworkCapabilities.TRANSPORT_WIFI)        -> true
                        hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)    -> true
                        hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)    -> true
                        else                                                    -> false
                    }
                }
            }
        } else {
            val networkInfo = cm.activeNetworkInfo
            result = (networkInfo !=null && networkInfo.isConnected)
        }

        return result
    }

    companion object {
        private const val DELAY_EXIT = 2000L
    }
}
