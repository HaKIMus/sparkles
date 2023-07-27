package com.hakim.infrastructure.eventstore

import com.hakim.domain.AggregateId
import com.hakim.domain.event.DomainEvent
import kotlinx.coroutines.flow.Flow

interface   EventStore {
    suspend fun persist(event: DomainEvent)
    suspend fun persist(events: List<DomainEvent>)
    suspend fun read(aggregateId: AggregateId): Flow<DomainEvent>
    suspend fun readAll(): Flow<Flow<DomainEvent>>
}