package com.example.mornin_mobile

import com.example.mornin_mobile.domain.entity.ArticleEntity

sealed interface MorninState {

    data class Loaded(
        val articles: List<ArticleEntity>
    ): MorninState

    data class Loading(
        val articles: List<ArticleEntity> = emptyList()
    ): MorninState

    data class Error(
        val message: String
    ): MorninState
}