package com.hakim.application.command

import com.hakim.application.UnitCommandHandler
import com.hakim.domain.Library
import com.hakim.infrastructure.eventstore.EventStore
import jakarta.inject.Inject
import jakarta.inject.Singleton

@Singleton
class InitializeLibraryBookCommandHandler @Inject constructor(
    private val eventStore: EventStore,
) : UnitCommandHandler<InitializeLibraryBookCommand> {
    override suspend fun handle(command: InitializeLibraryBookCommand) {
        val library = Library.newLibrary(command.aggregateId)

        eventStore.persist(library.changes)
    }
}