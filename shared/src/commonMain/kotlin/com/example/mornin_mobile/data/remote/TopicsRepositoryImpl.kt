package com.example.mornin_mobile.data.remote

import com.example.mornin_mobile.data.remote.dto.CreateTopicRequest
import com.example.mornin_mobile.data.remote.dto.TopicDto
import com.example.mornin_mobile.domain.entity.TopicEntity
import com.example.mornin_mobile.domain.repository.TopicsRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class TopicsRepositoryImpl(
    private val client: HttpClient,
    private val baseUrl: String
) : TopicsRepository {

    override suspend fun getTopics(): List<TopicEntity> {
        val dtos: List<TopicDto> = client.get("$baseUrl/topics").body()
        return dtos.map { TopicEntity(id = it.id, name = it.name) }
    }

    override suspend fun createTopic(name: String): TopicEntity {
        val dto: TopicDto = client.post("$baseUrl/topics") {
            contentType(ContentType.Application.Json)
            setBody(CreateTopicRequest(name))
        }.body()
        return TopicEntity(id = dto.id, name = dto.name)
    }

    override suspend fun deleteTopic(id: String): Boolean {
        client.delete("$baseUrl/topics/$id")
        return true
    }
}
