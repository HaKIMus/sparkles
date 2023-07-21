package com.hakim.infrastructure.eventstore.proxy

import com.hakim.domain.AggregateId
import com.hakim.domain.Library
import com.hakim.domain.service.LibraryReconstruction
import com.hakim.infrastructure.eventstore.EventStore
import io.github.reactivecircus.cache4k.Cache
import jakarta.inject.Singleton
import kotlin.time.Duration.Companion.seconds

@Singleton
class LibraryCacheReadEventStoreProxy(
    private val eventStore: EventStore,
    private val reconstruction: LibraryReconstruction,
    private val cache: Cache<AggregateId, Library> = Cache.Builder()
        .expireAfterAccess(60.seconds)
        .build()
) : ReadEventStoreProxy<Library> {
    override suspend fun read(aggregateId: AggregateId): Library {
        val library = cache.get(aggregateId) {
            reconstruction.reconstructAsync(eventStore.read(aggregateId)).await()
        }

        return library
    }

    override suspend fun readAll(): List<Library> {
        if (cache.asMap().isEmpty()) {
            eventStore.readAll().collect {
                val library = reconstruction.reconstructAsync(it).await()
                cache.put(library.id, library)
            }
        }

        return cache.asMap().values.toList()
    }
}