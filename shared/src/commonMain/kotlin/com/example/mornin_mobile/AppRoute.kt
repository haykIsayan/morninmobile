package com.example.mornin_mobile

import kotlinx.serialization.Serializable

sealed interface AppRoute {
    @Serializable
    data object Auth : AppRoute

    @Serializable
    data object Mornin : AppRoute
}
