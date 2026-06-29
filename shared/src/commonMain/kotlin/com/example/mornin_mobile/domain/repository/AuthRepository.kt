package com.example.mornin_mobile.domain.repository

interface AuthRepository {
    suspend fun requestOtp(recipient: String): Boolean
    suspend fun verifyOtp(recipient: String, otp: String): String?
}