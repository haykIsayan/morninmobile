package com.example.mornin_mobile.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class TopicDto(
    val id: String,
    val name: String
)

@Serializable
data class CreateTopicRequest(
    val name: String
)
