package com.example.mornin_mobile.data.local

import android.content.Context

class AndroidTokenStorage(context: Context) : TokenStorage {

    private val prefs = context.applicationContext
        .getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    override fun saveToken(token: String) {
        prefs.edit().putString(KEY_TOKEN, token).apply()
    }

    override fun getToken(): String? = prefs.getString(KEY_TOKEN, null)

    override fun clearToken() {
        prefs.edit().remove(KEY_TOKEN).apply()
    }

    companion object {
        private const val PREFS_NAME = "mornin_prefs"
        private const val KEY_TOKEN = "jwt_token"
    }
}
