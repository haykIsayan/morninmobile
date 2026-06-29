package com.example.mornin_mobile.data.local

class TokenProvider(private val storage: TokenStorage) {
    fun getToken(): String? = storage.getToken()
    fun setToken(token: String) = storage.saveToken(token)
    fun clearToken() = storage.clearToken()
}
