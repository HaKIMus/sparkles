package com.hakim.application.command

import com.hakim.domain.AggregateId
import com.hakim.domain.Library
import com.hakim.domain.event.DomainEvent
import com.hakim.domain.event.LibraryInitialized
import com.hakim.infrastructure.eventstore.EventStore
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*

class InitializeLibraryBookCommandHandlerTest {
    private lateinit var eventStore: EventStore
    private lateinit var commandHandler: InitializeLibraryBookCommandHandler

    @BeforeEach
    fun setUp() {
        eventStore = mockk(relaxed = true)
        commandHandler = InitializeLibraryBookCommandHandler(eventStore)
    }

    @Test
    fun `handle() should persist new library changes to eventStore`() = runBlocking {
        val aggregateId = AggregateId.fromUuid(UUID.randomUUID())
        val command = InitializeLibraryBookCommand(aggregateId)
        val library = Library.newLibrary(aggregateId)
        coEvery { eventStore.persist(match<List<DomainEvent>> {
            it.size == library.changes.size &&
                    it.first() is LibraryInitialized &&
                    (it.first() as LibraryInitialized).libraryId == library.changes.first().aggregateId
        }) } returns Unit

        commandHandler.handle(command)

        coVerify {
            eventStore.persist(match<List<DomainEvent>> {
                it.size == library.changes.size &&
                        it.first() is LibraryInitialized &&
                        (it.first() as LibraryInitialized).libraryId == library.changes.first().aggregateId
            })
        }
    }
}