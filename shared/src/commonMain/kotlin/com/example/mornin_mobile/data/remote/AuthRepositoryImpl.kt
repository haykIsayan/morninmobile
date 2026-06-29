package com.example.mornin_mobile.data.remote

import com.example.mornin_mobile.data.remote.dto.RequestOtpRequest
import com.example.mornin_mobile.data.remote.dto.VerifyOtpRequest
import com.example.mornin_mobile.data.remote.dto.VerifyOtpResponseDto
import com.example.mornin_mobile.domain.repository.AuthRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess

class AuthRepositoryImpl(
    private val client: HttpClient,
    private val baseUrl: String
) : AuthRepository {

    override suspend fun requestOtp(recipient: String): Boolean {
        val response: HttpResponse = client.post("$baseUrl/auth/request-otp") {
            contentType(ContentType.Application.Json)
            setBody(RequestOtpRequest(otpRecipient = recipient))
        }
        return response.status.isSuccess()
    }

    override suspend fun verifyOtp(recipient: String, otp: String): String? {
        return runCatching {
            client.post("$baseUrl/auth/verify-otp") {
                contentType(ContentType.Application.Json)
                setBody(VerifyOtpRequest(otpRecipient = recipient, code = otp))
            }.body<VerifyOtpResponseDto>().token
        }.getOrNull()
    }
}