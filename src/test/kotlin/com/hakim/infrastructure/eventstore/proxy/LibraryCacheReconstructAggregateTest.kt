package com.hakim.infrastructure.eventstore.proxy

import com.hakim.domain.AggregateId
import com.hakim.domain.Library
import com.hakim.domain.service.LibraryReconstruction
import com.hakim.infrastructure.eventstore.EventStore
import com.hakim.infrastructure.eventstore.service.LibraryCacheReconstructAggregate
import io.github.reactivecircus.cache4k.Cache
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.*

class LibraryCacheReconstructAggregateTest {
    @Test
    fun `read method retrieves library from cache if present`() = runBlocking {
        val mockEventStore = mockk<EventStore>()
        val mockReconstruction = mockk<LibraryReconstruction>()
        val mockCache = mockk<Cache<AggregateId, Library>>()
        val testAggregateId = AggregateId(UUID.randomUUID())

        val testLibrary = Library.newLibrary(testAggregateId)

        coEvery { mockCache.get(any(), any()) } returns testLibrary

        val proxy = LibraryCacheReconstructAggregate(mockEventStore, mockReconstruction, mockCache)

        val result = proxy.reconstruct(testAggregateId)

        assertEquals(testLibrary, result)
        coVerify(exactly = 0) { mockEventStore.read(testAggregateId) }
    }
}