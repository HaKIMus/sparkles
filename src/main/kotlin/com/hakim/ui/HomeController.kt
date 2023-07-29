package com.hakim.ui

import com.hakim.domain.AggregateId
import com.hakim.domain.event.BookRegistered
import com.hakim.domain.event.LibraryInitialized
import com.hakim.domain.service.LibraryReconstruction
import com.hakim.infrastructure.broker.producer.TestClient
import com.hakim.infrastructure.eventstore.EventStore
import com.hakim.infrastructure.eventstore.EventStoreQualifier
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Produces
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.*

@Controller("/")
class HomeController(
    @EventStoreQualifier private val eventStore: EventStore,
    private val json: Json,
    private val libraryReconstruction: LibraryReconstruction,
    private val testClient: TestClient,
) {
    @Get
    @Produces("text/plain")
    suspend fun index(): String {
        val aggregateId = AggregateId.random()
        eventStore.persist(LibraryInitialized(aggregateId))
        val bookId = UUID.randomUUID()
        eventStore.persist(BookRegistered(aggregateId, bookId))
        //eventStore.persist(BookBorrowed(aggregateId, bookId))

        testClient.send("test", "key", "$bookId")

        return json.encodeToString(libraryReconstruction.reconstructAsync(eventStore.read(aggregateId)).await())
    }
}