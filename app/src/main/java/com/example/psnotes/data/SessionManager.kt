package com.example.psnotes.data

import android.content.Context
import androidx.core.content.edit

object SessionManager {
    private const val PREFS_NAME = "app_preferences"
    private const val PREFS_KEY_USERNAME = "username"
    private const val PREFS_KEY_PIN = "pin"
    private const val PREFS_KEY_LOGGED_IN = "logged_in"
    private const val PREFS_KEY_WORKER_ID = "worker_id"
    private const val SHARED_PREFS_NAME = "session_prefs"
    private const val KEY_LOGIN_DETAILS = "login_details"

    // Guardar detalles de login
    fun saveLoginDetails(context: Context, username: String, password: String) {
        val sharedPreferences = context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        // Guardar el nombre de usuario y la contraseña
        editor.putString(KEY_LOGIN_DETAILS, "$username:$password")
        editor.apply()
    }

    // Obtener detalles de login
    fun getLoginDetails(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString(KEY_LOGIN_DETAILS, null)
    }

    // Eliminar detalles de login al cerrar sesión
    fun clearLoginDetails(context: Context) {
        val sharedPreferences = context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)
        sharedPreferences.edit() {

            // Eliminar los detalles de login guardados
            remove(KEY_LOGIN_DETAILS)
        }
    }

    fun saveLoginDetails(context: Context, username: String, pin: Int, workerId: String) {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putString(PREFS_KEY_USERNAME, username)
            putInt(PREFS_KEY_PIN, pin)
            putString(PREFS_KEY_WORKER_ID, workerId)
            apply()
        }
    }

    fun getWorkerId(context: Context): String {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString(PREFS_KEY_WORKER_ID, null).toString()
    }

    fun getSavedLoginDetails(context: Context): Triple<String?, Int?, String?> {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val username = sharedPreferences.getString(PREFS_KEY_USERNAME, null)
        val pin = sharedPreferences.getInt(PREFS_KEY_PIN, -1)
        val workerId = sharedPreferences.getString(PREFS_KEY_WORKER_ID, null)
        return Triple(username, pin.takeIf { it != -1 }, workerId)
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