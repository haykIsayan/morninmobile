package com.example.mornin_mobile.domain

import com.example.mornin_mobile.domain.repository.AuthRepository

class VerifyOtpUseCase(
    private val authRepository: AuthRepository
) {
    suspend fun execute(recipient: String, otp: String): String? {
        return authRepository.verifyOtp(recipient, otp)
    }
}