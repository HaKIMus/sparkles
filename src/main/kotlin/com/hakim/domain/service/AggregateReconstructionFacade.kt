package com.hakim.domain.service

import com.hakim.domain.Aggregate
import com.hakim.domain.AggregateId
import com.hakim.infrastructure.eventstore.CachedEventStoreQualifier
import com.hakim.infrastructure.eventstore.EventStore

class AggregateReconstructionFacade<T : Aggregate>(
    @CachedEventStoreQualifier private val eventStore: EventStore,
    private val aggregateReconstruction: AggregateReconstruction<T>,
) {
    suspend fun reconstruct(aggregateId: AggregateId) : T {
        return aggregateReconstruction.reconstruct(eventStore.read(aggregateId))
    }
}