package com.hakim.ui.api

import com.hakim.domain.AggregateId
import com.hakim.domain.Library
import com.hakim.infrastructure.eventstore.service.ReconstructAggregate
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get

@Controller("/api")
class LibraryController(
    private val libraryEventStore: ReconstructAggregate<Library>
) {
    @Get("/v1/libraries")
    suspend fun listLibraries(): List<Library> {
        return libraryEventStore.reconstructAll()
    }

    @Get("/v1/libraries/{libraryId}")
    suspend fun findLibrary(libraryId: String): Library {
        return libraryEventStore.reconstruct(AggregateId.fromString(libraryId))
    }
}