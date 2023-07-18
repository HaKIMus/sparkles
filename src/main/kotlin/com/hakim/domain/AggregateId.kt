package com.hakim.domain

import com.hakim.infrastructure.serialization.AggregateIdSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable(with = AggregateIdSerializer::class)
data class AggregateId(val id: UUID) {
    companion object {
        fun random(): AggregateId {
            return AggregateId(UUID.randomUUID())
        }

        fun fromUuid(uuid: UUID): AggregateId {
            return AggregateId(uuid)
        }
    }
}