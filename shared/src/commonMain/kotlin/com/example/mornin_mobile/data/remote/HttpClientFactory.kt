package com.example.mornin_mobile.data.remote

import com.example.mornin_mobile.data.local.TokenProvider
import io.ktor.client.HttpClient
import io.ktor.client.plugins.api.createClientPlugin
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object HttpClientFactory {
    fun create(tokenProvider: TokenProvider): HttpClient = HttpClient {
        expectSuccess = true
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
            })
        }
        install(Logging) {
            logger = Logger.SIMPLE
            level = LogLevel.ALL
        }
        install(createClientPlugin("AuthInterceptor") {
            onRequest { request, _ ->
                tokenProvider.getToken()?.let { token ->
                    request.headers.append(HttpHeaders.Authorization, "Bearer $token")
                }
            }
        })
    }
}
