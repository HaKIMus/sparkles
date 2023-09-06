package com.hakim.application.command

import com.hakim.application.UnitCommandHandler
import com.hakim.domain.Library
import com.hakim.domain.service.AggregateReconstructionFacade
import com.hakim.infrastructure.eventstore.EventStore
import com.hakim.infrastructure.eventstore.EventStoreQualifier
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RegisterBookCommandHandler(
    private val aggregateReconstruction: AggregateReconstructionFacade<Library>,
    @EventStoreQualifier private val eventStore: EventStore,
) : UnitCommandHandler<RegisterBookCommand> {
    override suspend fun handle(command: RegisterBookCommand) = withContext(Dispatchers.IO) {
        val library = aggregateReconstruction.reconstruct(command.libraryId)
        library.registerBook(command.book)
        eventStore.persist(library.changes)
    }
}