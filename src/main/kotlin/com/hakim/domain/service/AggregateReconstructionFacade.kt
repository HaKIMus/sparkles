package com.hakim.domain.service

import com.hakim.domain.Aggregate
import com.hakim.domain.AggregateId
import com.hakim.infrastructure.eventstore.CachedEventStoreQualifier
import com.hakim.infrastructure.eventstore.EventStore
import kotlinx.coroutines.Deferred

class AggregateReconstructionFacade<T : Aggregate>(
    @CachedEventStoreQualifier private val eventStore: EventStore,
    private val aggregateReconstruction: AggregateReconstruction<T>,
) {
    suspend fun reconstructAsync(aggregateId: AggregateId) : Deferred<T> {
        return aggregateReconstruction.reconstructAsync(eventStore.read(aggregateId))
    }
}