package com.breadkun.backend.application.port.output

import com.breadkun.backend.infrastructure.persistence.entity.CafeCartEntity
import java.time.LocalDateTime

interface CafeCartQueryPort {
    suspend fun findActiveByMultipleOptions(
        createdById: String?,
        currentTime: LocalDateTime
    ): List<CafeCartEntity>

    suspend fun findActiveById(
        cafeCartId: String,
        currentTime: LocalDateTime
    ): CafeCartEntity?
}