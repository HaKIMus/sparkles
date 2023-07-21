package com.hakim.infrastructure.eventstore

import com.hakim.domain.AggregateId
import com.hakim.domain.event.BookRegistered
import com.hakim.domain.event.LibraryInitialized
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import java.util.*

class InMemoryEventStoreTest {
    @Test
    fun `persist a domain event`() = runBlocking {
        val aggregateId = AggregateId.random()
        val event = LibraryInitialized(aggregateId)

        val eventStore = InMemoryEventStore()
        eventStore.persist(event)

        assert(eventStore.read(aggregateId).toList().isNotEmpty())
    }

    @Test
    fun `read a domain event`() = runBlocking {
        val aggregateId = AggregateId.random()
        val event = LibraryInitialized(aggregateId)

        val eventStore = InMemoryEventStore()
        eventStore.persist(event)

        val readEvents = eventStore.read(aggregateId)
        assert(readEvents.toList().first().aggregateId == aggregateId)
    }

    @Test
    fun `read all domain events`() = runBlocking {
        val aggregateId = AggregateId.random()
        val libraryInitialized = LibraryInitialized(aggregateId)
        val bookRegistered = BookRegistered(aggregateId, UUID.randomUUID())

        val eventStore = InMemoryEventStore()
        eventStore.persist(listOf(libraryInitialized, bookRegistered))

        assert(eventStore.readAll().count() == 1)
        assert(eventStore.readAll().first().count() == 2)
    }
}