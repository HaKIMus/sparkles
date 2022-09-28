package com.hakim.domain.service

import com.hakim.domain.AggregateId
import com.hakim.domain.event.BookRegistered
import com.hakim.domain.event.DomainEvent
import com.hakim.domain.event.LibraryInitialized
import org.junit.jupiter.api.Test
import java.util.*

class LibraryReconstructionTest {
    @Test
    fun `reconstruct`() {
        val reconstruction = LibraryReconstruction()
        val aggregateId = AggregateId.random()
        val events = mutableListOf<DomainEvent>(
            LibraryInitialized(aggregateId),
            BookRegistered(aggregateId, UUID.randomUUID()),
        )

        val library = reconstruction.reconstruct(events)
        assert(library.aggregateId == aggregateId)
        assert(library.books.isNotEmpty())
    }

    @Test
    fun `reconstruct from unsorted events`() {
        val reconstruction = LibraryReconstruction()
        val aggregateId = AggregateId.random()
        val initEvent = LibraryInitialized(aggregateId)

        val events = mutableListOf<DomainEvent>(
            BookRegistered(aggregateId, UUID.randomUUID()),
            initEvent,
        )

        val library = reconstruction.reconstruct(events)
        assert(library.aggregateId == aggregateId)
        assert(library.books.isNotEmpty())
    }
}