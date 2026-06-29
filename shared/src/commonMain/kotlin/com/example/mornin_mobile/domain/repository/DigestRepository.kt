package com.example.mornin_mobile.domain.repository

import com.example.mornin_mobile.domain.entity.DigestEntity

interface DigestRepository {
    suspend fun getDigestForToday(): DigestEntity
}