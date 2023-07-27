package com.hakim.infrastructure.eventstore.proxy

import com.hakim.domain.AggregateId
import com.hakim.domain.event.DomainEvent
import com.hakim.infrastructure.eventstore.CachedEventStoreQualifier
import com.hakim.infrastructure.eventstore.EventStore
import com.hakim.infrastructure.eventstore.EventStoreQualifier
import io.github.reactivecircus.cache4k.Cache
import jakarta.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.first
import java.time.Duration
import java.time.Instant

@Singleton
@CachedEventStoreQualifier
class MongoEventStoreCacheProxy(
    private val cache: Cache<AggregateId, Flow<DomainEvent>>,
    @EventStoreQualifier private val eventStore: EventStore
) : EventStore by eventStore {
    private var lastReadAllTime: Instant = Instant.MIN

    override suspend fun read(aggregateId: AggregateId): Flow<DomainEvent> {
        return cache.get(aggregateId) {
            eventStore.read(aggregateId)
        }
    }

    override suspend fun readAll(): Flow<Flow<DomainEvent>> {
        val now = Instant.now()

        if (Duration.between(lastReadAllTime, now).toMinutes() >= 1) {
            cache.invalidateAll()

            eventStore.readAll().collect {
                cache.put(it.first().aggregateId, it)
            }

            lastReadAllTime = now
        }

        return cache.asMap().values.asFlow()
    }
}