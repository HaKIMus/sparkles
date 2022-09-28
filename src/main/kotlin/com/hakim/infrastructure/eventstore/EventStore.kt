package com.hakim.infrastructure.eventstore

import com.hakim.domain.AggregateId
import com.hakim.domain.event.DomainEvent

interface EventStore {
    suspend fun persist(event: DomainEvent)
    suspend fun persist(event: List<DomainEvent>)
    suspend fun read(aggregateId: AggregateId): List<DomainEvent>
}