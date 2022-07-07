package com.hakim.domain

import java.util.UUID

data class AggregateId(private val id: UUID) {
    companion object {
        fun random(): AggregateId {
            return AggregateId(UUID.randomUUID())
        }

        fun fromUuid(uuid: UUID): AggregateId {
            return AggregateId(uuid)
        }
    }
}