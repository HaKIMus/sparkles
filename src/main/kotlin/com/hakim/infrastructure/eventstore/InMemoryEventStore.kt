package com.hakim.infrastructure.eventstore

import com.hakim.domain.AggregateId
import com.hakim.domain.event.DomainEvent
import java.util.*

class InMemoryEventStore : EventStore {
    private val events: MutableMap<AggregateId, MutableList<DomainEvent>> = mutableMapOf()

    override suspend fun persist(event: DomainEvent) {
        (events[event.aggregateId]?.add(event)) ?: events.put(event.aggregateId, mutableListOf(event))
    }

    override suspend fun read(aggregateId: AggregateId): List<DomainEvent> {
        return events[aggregateId]?.toList() ?: listOf()
    }
}