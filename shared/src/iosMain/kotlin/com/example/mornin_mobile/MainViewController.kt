package com.example.mornin_mobile

import androidx.compose.ui.window.ComposeUIViewController
import com.example.mornin_mobile.data.local.IosTokenStorage

fun MainViewController() = ComposeUIViewController {
    App(tokenStorage = IosTokenStorage())
}