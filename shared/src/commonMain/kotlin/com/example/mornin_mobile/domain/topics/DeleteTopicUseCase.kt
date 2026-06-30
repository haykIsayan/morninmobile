package com.example.mornin_mobile.domain.topics

import com.example.mornin_mobile.domain.repository.TopicsRepository

class DeleteTopicUseCase(
    private val topicRepository: TopicsRepository
) {
    suspend fun execute(topicId: String): Boolean {
        return topicRepository.deleteTopic(topicId)
    }
}