package com.example.mornin_mobile.domain.repository

import com.example.mornin_mobile.domain.entity.TopicEntity

interface TopicsRepository {
    suspend fun getTopics(): List<TopicEntity>
    suspend fun createTopic(name: String): TopicEntity
    suspend fun deleteTopic(id: String): Boolean
}