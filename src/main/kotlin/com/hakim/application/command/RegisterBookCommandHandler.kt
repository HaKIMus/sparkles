package com.hakim.application.command

import com.hakim.application.UnitCommandHandler
import com.hakim.domain.Library
import com.hakim.domain.service.AggregateReconstructionFacade
import com.hakim.infrastructure.eventstore.EventStore

class RegisterBookCommandHandler(
    private val aggregateReconstruction: AggregateReconstructionFacade<Library>,
    private val eventStore: EventStore,
) : UnitCommandHandler<RegisterBookCommand> {
    override suspend fun handle(command: RegisterBookCommand) {
        val library = aggregateReconstruction.reconstruct(command.libraryId)
        library.registerBook(command.book)
        eventStore.persist(library._changes)
    }
}