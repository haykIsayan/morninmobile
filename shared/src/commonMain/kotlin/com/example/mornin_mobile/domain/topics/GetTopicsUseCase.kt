package com.example.mornin_mobile.domain.topics

import com.example.mornin_mobile.domain.entity.TopicEntity
import com.example.mornin_mobile.domain.repository.TopicsRepository

class GetTopicsUseCase(
    private val topicsRepository: TopicsRepository
) {
    suspend fun execute(): List<TopicEntity> = topicsRepository.getTopics()
}
