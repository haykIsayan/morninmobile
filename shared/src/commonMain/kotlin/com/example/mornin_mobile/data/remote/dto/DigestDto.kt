package com.example.mornin_mobile.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DigestDto(
    @SerialName("digest_id") val digestId: String,
    @SerialName("user_id") val userId: String,
    val articles: List<ArticleDto>
)

@Serializable
data class ArticleDto(
    val topic: String,
    val title: String,
    val summary: String,
    val source: String,
    val url: String,
    @SerialName("published_date") val publishedDate: String
)
