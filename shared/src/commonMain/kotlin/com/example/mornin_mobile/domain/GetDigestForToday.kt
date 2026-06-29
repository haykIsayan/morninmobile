package com.example.mornin_mobile.domain

import com.example.mornin_mobile.domain.entity.DigestEntity
import com.example.mornin_mobile.domain.exception.NoDigestsFoundException
import com.example.mornin_mobile.domain.exception.UnauthorizedException
import com.example.mornin_mobile.domain.repository.DigestRepository
import io.ktor.client.plugins.ClientRequestException
import io.ktor.http.HttpStatusCode

class GetDigestForToday(
    private val digestRepository: DigestRepository
) {
    suspend fun execute(): DigestEntity {
        try {
            return digestRepository.getDigestForToday()
        } catch (e: ClientRequestException) {
            when (e.response.status) {
                HttpStatusCode.NotFound -> throw NoDigestsFoundException()
                HttpStatusCode.Unauthorized -> throw UnauthorizedException()
                else -> throw e
            }
        }
    }
}