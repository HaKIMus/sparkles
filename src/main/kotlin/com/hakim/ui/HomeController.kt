package com.hakim.ui

import com.hakim.domain.AggregateId
import com.hakim.domain.event.BookBorrowed
import com.hakim.domain.event.BookRegistered
import com.hakim.domain.event.LibraryInitialized
import com.hakim.domain.service.LibraryReconstruction
import com.hakim.infrastructure.eventstore.MongoEventStore
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Produces
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.*

@Controller("/")
class HomeController(
    private val eventStore: MongoEventStore,
    private val json: Json,
    private val libraryReconstruction: LibraryReconstruction
) {
    @Get
    @Produces("text/plain")
    suspend fun index(): String {
        val aggregateId = AggregateId.random()
        eventStore.persist(LibraryInitialized(aggregateId))
        val bookId = UUID.randomUUID()
        eventStore.persist(BookRegistered(aggregateId, bookId))
        eventStore.persist(BookBorrowed(aggregateId, bookId))

        return json.encodeToString(libraryReconstruction.reconstructAsync(eventStore.read(aggregateId)))
    }
}