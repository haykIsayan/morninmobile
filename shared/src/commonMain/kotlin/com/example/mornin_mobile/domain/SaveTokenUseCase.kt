package com.example.mornin_mobile.domain

import com.example.mornin_mobile.data.local.TokenProvider

class SaveTokenUseCase(
    private val tokenProvider: TokenProvider
) {
    fun execute(token: String) {
        tokenProvider.setToken(token)
    }
}
