package com.example.mornin_mobile.data.local

import platform.Foundation.NSUserDefaults

class IosTokenStorage : TokenStorage {

    private val defaults = NSUserDefaults.standardUserDefaults

    override fun saveToken(token: String) {
        defaults.setObject(token, forKey = KEY_TOKEN)
    }

    override fun getToken(): String? = defaults.stringForKey(KEY_TOKEN)

    override fun clearToken() {
        defaults.removeObjectForKey(KEY_TOKEN)
    }

    companion object {
        private const val KEY_TOKEN = "jwt_token"
    }
}
