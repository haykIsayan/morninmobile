package com.example.mornin_mobile.domain.topics

import com.example.mornin_mobile.domain.entity.TopicEntity
import com.example.mornin_mobile.domain.repository.TopicsRepository

class CreateTopicUseCase(
    private val topicRepository: TopicsRepository
) {
    suspend fun execute(name: String): TopicEntity {
        return topicRepository.createTopic(name)
    }
}