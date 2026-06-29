package com.example.mornin_mobile.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestOtpRequest(
    @SerialName("otp_recipient") val otpRecipient: String
)

@Serializable
data class VerifyOtpRequest(
    @SerialName("otp_recipient") val otpRecipient: String,
    val code: String
)

@Serializable
data class VerifyOtpResponseDto(
    val token: String
)
