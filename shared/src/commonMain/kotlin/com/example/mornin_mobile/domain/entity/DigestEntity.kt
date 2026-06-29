package com.example.mornin_mobile.domain.entity

data class DigestEntity(
    val userId: String,
    val digestId: String,
    val articles: List<ArticleEntity>
)