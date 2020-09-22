package com.tan.master_detail42

import android.content.Context
import android.content.SharedPreferences

/**
 * Persistence mechanism for saving and reusing data within the app.
 * Using Singleton design pattern which is very helpful for checking/using persistent data.
 */
object AppPreferences {

    private const val SHARED_PREFS_NAME = "MD42_Preferences"
    private const val MODE = Context.MODE_PRIVATE
    private lateinit var kPrefs: SharedPreferences

    // App-specific preferences and their default values
    private val kLastVisit = Pair("LastVisit", "")
    private val kGridLayout = Pair("IsGridLayout", true)

    fun init(context: Context) {
        kPrefs = context.getSharedPreferences(SHARED_PREFS_NAME, MODE)
    }

    /**
     * SharedPreferences extension function, so we won't need to call edit() and apply()
     * ourselves on every SharedPreferences operation.
     */
    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = edit()
        operation(editor)
        editor.apply()
    }

    var lastVisit: String?
        // custom getter to get a preference of a desired type, with a predefined default value
        get() = kPrefs.getString(kLastVisit.first, kLastVisit.second)
        // custom setter to save a preference back to preferences file
        set(value) = kPrefs.edit { it.putString(kLastVisit.first, value) }

    var isGridLayout: Boolean
        get() = kPrefs.getBoolean(kGridLayout.first, kGridLayout.second)
        set(value) = kPrefs.edit { it.putBoolean(kGridLayout.first, value) }

}