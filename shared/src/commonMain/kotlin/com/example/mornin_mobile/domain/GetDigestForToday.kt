package com.example.mornin_mobile.domain

import com.example.mornin_mobile.domain.entity.DigestEntity
import com.example.mornin_mobile.domain.repository.DigestRepository

class GetDigestForToday(
    private val digestRepository: DigestRepository
) {
    suspend fun execute(): DigestEntity {
        return digestRepository.getDigestForToday()
    }
}