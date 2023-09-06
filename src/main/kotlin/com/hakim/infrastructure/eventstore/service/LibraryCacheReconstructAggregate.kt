package com.hakim.infrastructure.eventstore.service

import com.hakim.domain.AggregateId
import com.hakim.domain.Library
import com.hakim.domain.service.LibraryReconstruction
import com.hakim.infrastructure.eventstore.CachedEventStoreQualifier
import com.hakim.infrastructure.eventstore.EventStore
import io.github.reactivecircus.cache4k.Cache
import jakarta.inject.Singleton
import java.time.Duration
import java.time.Instant

@Singleton
class LibraryCacheReconstructAggregate(
    @CachedEventStoreQualifier private val eventStore: EventStore,
    private val reconstruction: LibraryReconstruction,
    private val cache: Cache<AggregateId, Library>
) : ReconstructAggregate<Library> {
    private var lastReadAllTime: Instant = Instant.MIN

    override suspend fun reconstruct(aggregateId: AggregateId): Library {
        return cache.get(aggregateId) {
            reconstruction.reconstruct(eventStore.read(aggregateId))
        }
    }

    override suspend fun reconstructAll(): List<Library> {
        val now = Instant.now()

        if (Duration.between(lastReadAllTime, now).toMinutes() >= 1) {
            cache.invalidateAll()

            eventStore.readAll().collect {
                val library = reconstruction.reconstruct(it)
                cache.put(library.id, library)
            }

            lastReadAllTime = now
        }

        return cache.asMap().values.toList()
    }
}