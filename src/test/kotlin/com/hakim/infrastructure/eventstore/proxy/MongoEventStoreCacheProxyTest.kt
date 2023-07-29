package com.hakim.infrastructure.eventstore.proxy

import com.hakim.domain.AggregateId
import com.hakim.domain.event.DomainEvent
import com.hakim.domain.event.LibraryInitialized
import com.hakim.infrastructure.eventstore.EventStore
import io.github.reactivecircus.cache4k.Cache
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MongoEventStoreCacheProxyTest {
    private lateinit var cache: Cache<AggregateId, Flow<DomainEvent>>
    private lateinit var eventStore: EventStore
    private lateinit var mongoEventStoreCacheProxy: MongoEventStoreCacheProxy

    @BeforeEach
    fun setUp() {
        cache = mockk(relaxed = true)
        eventStore = mockk(relaxed = true)
        mongoEventStoreCacheProxy = MongoEventStoreCacheProxy(cache, eventStore)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `read returns cached value if present`() = runTest {
        val aggregateId = AggregateId(UUID.randomUUID())
        val cachedFlow = flowOf<DomainEvent>()
        coEvery { cache.get(aggregateId, any()) } returns cachedFlow

        val result = mongoEventStoreCacheProxy.read(aggregateId)

        assert(result == cachedFlow)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `readAll refreshes cache every minute`() = runTest {
        val domainEventFlow = flowOf<DomainEvent>(LibraryInitialized(AggregateId.random()))
        coEvery { eventStore.readAll() } returns flowOf(domainEventFlow)
        coEvery { cache.invalidateAll() } returns Unit
        coEvery { cache.put(any(), any()) } returns Unit

        mongoEventStoreCacheProxy.readAll()

        coVerify { cache.invalidateAll() }
        coVerify { eventStore.readAll() }
    }
}