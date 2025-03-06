package com.example.telefonia

import android.content.Context
import android.content.SharedPreferences

object SharedPrefManager {
    private const val PREF_NAME = "AutoReplyPrefs"
    private const val KEY_NUMBER = "saved_number"
    private const val KEY_MESSAGE = "saved_message"

    fun saveNumber(context: Context, number: String) {
        val prefs: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit().putString(KEY_NUMBER, number).apply()
    }

    fun saveMessage(context: Context, message: String) {
        val prefs: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit().putString(KEY_MESSAGE, message).apply()
    }

    fun getSavedNumber(context: Context): String {
        val prefs: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return prefs.getString(KEY_NUMBER, "") ?: ""
    }

    fun getSavedMessage(context: Context): String {
        val prefs: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return prefs.getString(KEY_MESSAGE, "Lo siento, no puedo atender en este momento.") ?: ""
    }
}
