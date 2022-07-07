package com.hakim.infrastructure.eventstore

import com.hakim.domain.AggregateId
import com.hakim.domain.event.LibraryInitialized
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import java.util.UUID

class InMemoryEventStoreTest {
    @Test
    fun `persist a domain event`() = runBlocking {
        val aggregateId = AggregateId.random()
        val event = LibraryInitialized(aggregateId)

        val eventStore = InMemoryEventStore()
        eventStore.persist(event)

        assert(eventStore.read(aggregateId).isNotEmpty())
    }

    @Test
    fun `read a domain event`() = runBlocking {
        val aggregateId = AggregateId.random()
        val event = LibraryInitialized(aggregateId)

        val eventStore = InMemoryEventStore()
        eventStore.persist(event)

        val readEvents = eventStore.read(aggregateId)
        assert(readEvents.first().aggregateId == aggregateId)
    }
}