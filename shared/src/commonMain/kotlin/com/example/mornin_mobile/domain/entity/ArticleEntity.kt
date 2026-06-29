package com.example.mornin_mobile.domain.entity

data class ArticleEntity(
    val topic: String,
    val title: String,
    val summary: String,
    val source: String,
    val url: String,
    val publishedDate: String
)
