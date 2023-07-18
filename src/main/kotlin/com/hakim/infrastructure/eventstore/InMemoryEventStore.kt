package com.hakim.infrastructure.eventstore

import com.hakim.domain.AggregateId
import com.hakim.domain.event.DomainEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flowOf

class InMemoryEventStore : EventStore {
    private val events: MutableMap<AggregateId, MutableList<DomainEvent>> = mutableMapOf()

    override suspend fun persist(event: DomainEvent) {
        (events[event.aggregateId]?.add(event)) ?: events.put(event.aggregateId, mutableListOf(event))
    }

    override suspend fun persist(events: List<DomainEvent>) {
        events.forEach {
            persist(it)
        }
    }

    override suspend fun read(aggregateId: AggregateId): Flow<DomainEvent> {
        return events[aggregateId]?.toList()?.asFlow() ?: flowOf()
    }
}