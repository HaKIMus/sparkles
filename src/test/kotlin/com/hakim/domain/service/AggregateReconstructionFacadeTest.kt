package com.hakim.domain.service

import com.hakim.domain.AggregateId
import com.hakim.domain.Library
import com.hakim.infrastructure.eventstore.InMemoryEventStore
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test

class AggregateReconstructionFacadeTest {
    @Test
    fun `reconstruct`() = runBlocking {
        val aggregateId = AggregateId.random()
        val library = Library(aggregateId)

        val eventStore = InMemoryEventStore()
        eventStore.persist(library.changes)

        val facade = AggregateReconstructionFacade(
            eventStore,
            LibraryReconstruction(),
        )

        val reconstructedLibrary = facade.reconstruct(aggregateId)
        assert(reconstructedLibrary.id == aggregateId)
    }
}