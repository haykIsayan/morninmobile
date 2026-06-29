package com.example.mornin_mobile.features.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.mornin_mobile.domain.RequestOtpUseCase
import com.example.mornin_mobile.domain.SaveTokenUseCase
import com.example.mornin_mobile.domain.VerifyOtpUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    private val requestOtpUseCase: RequestOtpUseCase,
    private val verifyOtpUseCase: VerifyOtpUseCase,
    private val saveTokenUseCase: SaveTokenUseCase
) : ViewModel() {

    sealed interface AuthState {
        data object EnteringRecipient : AuthState
        data class RequestingOtp(val recipient: String) : AuthState
        data class EnteringOtp(val recipient: String) : AuthState
        data class VerifyingOtp(val recipient: String, val code: String) : AuthState
        data object Authenticated : AuthState
        data class Error(val message: String, val previousState: AuthState) : AuthState
    }

    private val _state = MutableStateFlow<AuthState>(AuthState.EnteringRecipient)
    val state: StateFlow<AuthState> = _state

    fun submitRecipient(recipient: String) {
        val current = _state.value
        if (current !is AuthState.EnteringRecipient && current !is AuthState.Error) return

        _state.value = AuthState.RequestingOtp(recipient)

        viewModelScope.launch {
            val success = runCatching { requestOtpUseCase.execute(recipient) }.getOrElse { false }
            _state.value = if (success) {
                AuthState.EnteringOtp(recipient)
            } else {
                AuthState.Error(
                    message = "Failed to send OTP. Please try again.",
                    previousState = AuthState.EnteringRecipient
                )
            }
        }
    }

    fun submitOtp(code: String) {
        val current = _state.value
        if (current !is AuthState.EnteringOtp) return

        _state.value = AuthState.VerifyingOtp(recipient = current.recipient, code = code)

        viewModelScope.launch {
            val token = runCatching {
                verifyOtpUseCase.execute(current.recipient, code)
            }.getOrNull()

            if (token != null) {
                saveTokenUseCase.execute(token)
                _state.value = AuthState.Authenticated
            } else {
                _state.value = AuthState.Error(
                    message = "Invalid OTP. Please try again.",
                    previousState = AuthState.EnteringOtp(current.recipient)
                )
            }
        }
    }

    fun retry() {
        val current = _state.value
        if (current is AuthState.Error) {
            _state.value = current.previousState
        }
    }

    companion object {
        fun factory(
            requestOtpUseCase: RequestOtpUseCase,
            verifyOtpUseCase: VerifyOtpUseCase,
            saveTokenUseCase: SaveTokenUseCase
        ): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                AuthViewModel(requestOtpUseCase, verifyOtpUseCase, saveTokenUseCase)
            }
        }
    }
}