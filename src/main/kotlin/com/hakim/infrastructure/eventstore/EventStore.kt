package com.hakim.infrastructure.eventstore

import com.hakim.domain.AggregateId
import com.hakim.domain.event.DomainEvent
import java.util.UUID

interface EventStore {
    suspend fun persist(event: DomainEvent)
    suspend fun read(aggregateId: AggregateId): List<DomainEvent>
}