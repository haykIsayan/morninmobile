package com.example.mornin_mobile.data.remote

import com.example.mornin_mobile.data.remote.dto.DigestDto
import com.example.mornin_mobile.domain.repository.DigestRepository
import com.example.mornin_mobile.domain.entity.ArticleEntity
import com.example.mornin_mobile.domain.entity.DigestEntity
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class DigestRepositoryImpl(
    private val client: HttpClient,
    private val baseUrl: String
) : DigestRepository {

    override suspend fun getDigestForToday(): DigestEntity {
        val digestDto: DigestDto = client
            .get("$baseUrl/digest")
            .body()

        return digestDto.toDomain()
    }

    private fun DigestDto.toDomain(): DigestEntity = DigestEntity(
        digestId = digestId,
        userId = userId,
        articles = articles.map { article ->
            ArticleEntity(
                topic = article.topic,
                title = article.title,
                summary = article.summary,
                source = article.source,
                url = article.url,
                publishedDate = article.publishedDate
            )
        }
    )
}