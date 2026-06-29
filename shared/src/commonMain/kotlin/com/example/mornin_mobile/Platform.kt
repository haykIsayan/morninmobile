package com.example.mornin_mobile

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform