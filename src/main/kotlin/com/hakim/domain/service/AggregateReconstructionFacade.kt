package com.hakim.domain.service

import com.hakim.domain.Aggregate
import com.hakim.domain.AggregateId
import com.hakim.infrastructure.eventstore.EventStore

class AggregateReconstructionFacade(
    private val eventStore: EventStore,
    private val aggregateReconstruction: LibraryReconstruction
) {
    suspend fun <T : Aggregate> reconstruct(aggregateId: AggregateId) : T {
        return aggregateReconstruction.reconstruct(eventStore.read(aggregateId)) as T
    }
}