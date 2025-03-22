package com.example.psnotes.data

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

object SessionManager {
    private const val PREFS_NAME = "app_preferences"
    private const val PREFS_KEY_USERNAME = "username"
    private const val PREFS_KEY_PIN = "pin"
    private const val PREFS_KEY_LOGGED_IN = "logged_in"

    fun saveLoginDetails(context: Context, username: String, pin: Int) {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putString(PREFS_KEY_USERNAME, username)
            putInt(PREFS_KEY_PIN, pin)
            apply()
        }
    }

    fun getSavedLoginDetails(context: Context): Pair<String?, Int?> {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val username = sharedPreferences.getString(PREFS_KEY_USERNAME, null)
        val pin = sharedPreferences.getInt(PREFS_KEY_PIN, -1)
        return Pair(username, pin.takeIf { it != -1 })
    }

    fun setLoggedIn(context: Context, isLoggedIn: Boolean) {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putBoolean(PREFS_KEY_LOGGED_IN, isLoggedIn)
            apply()
        }
    }

    fun isLoggedIn(context: Context): Boolean {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean(PREFS_KEY_LOGGED_IN, false)
    }

    fun clearSession(context: Context) {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            clear()
            apply()
        }
    }
}