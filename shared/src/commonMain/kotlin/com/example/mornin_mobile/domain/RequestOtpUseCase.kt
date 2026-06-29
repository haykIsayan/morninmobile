package com.example.mornin_mobile.domain

import com.example.mornin_mobile.domain.repository.AuthRepository

class RequestOtpUseCase(
    private val authRepository: AuthRepository
) {
    suspend fun execute(recipient: String): Boolean {
        return authRepository.requestOtp(recipient)
    }
}