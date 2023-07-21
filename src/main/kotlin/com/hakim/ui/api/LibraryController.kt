package com.hakim.ui.api

import com.hakim.domain.AggregateId
import com.hakim.domain.Library
import com.hakim.infrastructure.eventstore.proxy.ReadEventStoreProxy
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get

@Controller("/api")
class LibraryController(
    private val libraryEventStore: ReadEventStoreProxy<Library>
) {
    @Get("/v1/libraries")
    suspend fun listLibraries(): List<Library> {
        return libraryEventStore.readAll()
    }

    @Get("/v1/libraries/{libraryId}")
    suspend fun listLibraries(libraryId: String): Library {
        return libraryEventStore.read(AggregateId.fromString(libraryId))
    }
}